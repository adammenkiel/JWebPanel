package pl.publicprojects.pnettyclient.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.handler.ExceptionHandler;
import pl.publicprojects.pcommon.protocol.handler.decoder.PacketDecoder;
import pl.publicprojects.pcommon.protocol.handler.decoder.SizeDecoder;
import pl.publicprojects.pcommon.protocol.handler.encoder.PacketEncoder;
import pl.publicprojects.pcommon.protocol.handler.encoder.SizeEncoder;
import pl.publicprojects.pcommon.app.helper.ChatQueue;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.DisconnectPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessageGroupPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessagePacket;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.PingPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.PongPacket;
import pl.publicprojects.pnettyclient.handler.AbstractHandler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Main class for client with PCommon protocol
 * Spring boot uses it for connect to server hosted together with minecraft server by PPanelPlugin
 */
@Slf4j
@Getter
public class NettyClient extends AbstractConnection {

    private String host;
    private int port;
    private final PacketUtil packetUtil;
    private Channel channel;
    private final ChatQueue chatQueue;
    private final List<AbstractHandler> handlerList = new CopyOnWriteArrayList<>();
    private long ping = 0;

    public NettyClient() {
        this.packetUtil = new PacketUtil();
        this.packetUtil.registerClientPackets();
        this.chatQueue = new ChatQueue();
    }

    /**
     * Function for connect with PPanelPlugin server
     *
     * @param host Host of server
     * @param port Port of server
     */
    public void connect(String host, int port) {
        this.host = host;
        this.port = port;

        NettyClient client = this;
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline()
                                    .addLast(new SizeDecoder())
                                    .addLast(new PacketDecoder(packetUtil, client))
                                    .addLast(new SizeEncoder())
                                    .addLast(new PacketEncoder())
                                    .addLast(new ExceptionHandler(client))
                                    .addLast(new IdleStateHandler(60, 60, 0, TimeUnit.SECONDS));
                        }
                    });

            ChannelFuture future = bootstrap.connect(this.host, this.port).sync();
            future.channel().writeAndFlush(new JoinPacket());
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * Implementation for packet handling
     *
     * @param packet Received packet
     */
    @Override
    public void handle(Packet packet) {
        for(AbstractHandler handler : this.handlerList) {
            handler.handle(packet);
        }
        if(packet instanceof PingPacket pingPacket) {
            long difference = System.currentTimeMillis() - pingPacket.getTime();
            this.sendPacket(new PongPacket(System.currentTimeMillis()));
            this.ping = difference;
            log.info("Ping: {}", this.ping);
        }

        if(packet instanceof MessageGroupPacket messageGroupPacket) {
            messageGroupPacket.getMessages().forEach(this.chatQueue::add);
        }

        if(packet instanceof MessagePacket messagePacket) {
            log.info("Received message {}", messagePacket.getMessage());
            this.chatQueue.add(messagePacket.getMessage());
        }
        if(packet instanceof DisconnectPacket disconnectPacket) {
            log.info("Session disconnected! | Reason: {}", disconnectPacket.getMessage());
            this.disconnect();
        }
    }

    /**
     * Function executed while connection
     */
    @Override
    public void loginConnection(Object loginObject) {
        if(loginObject instanceof Channel channel) {
            this.channel = channel;
        }
    }

    @Override
    public void disconnect() {
        this.channel.disconnect();
    }

    @Override
    public void disconnectWithCause(Throwable throwable) {
        this.disconnect();
        throw new RuntimeException(throwable);
    }

    @Override
    public void sendPacket(Packet packet) {
        if(this.channel == null) return;
        this.channel.writeAndFlush(packet).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    @Override
    public String getName() {
        return "NettyClient";
    }

    @Override
    public void disconnectWithReason(String reason) {
        log.info("Disconnected: {}", reason);
        this.disconnect();
    }
}
