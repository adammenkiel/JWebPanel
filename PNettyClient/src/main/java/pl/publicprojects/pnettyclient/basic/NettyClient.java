package pl.publicprojects.pnettyclient.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.handler.decoder.PacketDecoder;
import pl.publicprojects.pcommon.protocol.handler.decoder.SizeDecoder;
import pl.publicprojects.pcommon.protocol.handler.encoder.PacketEncoder;
import pl.publicprojects.pcommon.protocol.handler.encoder.SizeEncoder;
import pl.publicprojects.pcommon.protocol.helper.ChatQueue;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessageGroupPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessagePacket;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;

@Getter
public class NettyClient extends AbstractConnection {

    private String host;
    private int port;
    private final PacketUtil packetUtil;
    private Channel channel;
    private final ChatQueue chatQueue;

    public NettyClient() {
        this.packetUtil = new PacketUtil();
        this.packetUtil.registerClientPackets();
        this.chatQueue = new ChatQueue();
    }

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
                                    .addLast(new PacketEncoder());
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

    @Override
    public void handle(Packet packet) {
        if(packet instanceof MessageGroupPacket messageGroupPacket) {
            messageGroupPacket.getMessages().forEach(this.chatQueue::add);
        }
        if(packet instanceof MessagePacket messagePacket) {
            System.out.println(messagePacket.getMessage());
            this.chatQueue.add(messagePacket.getMessage());
        }
    }

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
    public void sendPacket(Packet packet) {
        if(this.channel == null) return;
        this.channel.writeAndFlush(packet).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    @Override
    public String getName() {
        return "NettyClient";
    }
}
