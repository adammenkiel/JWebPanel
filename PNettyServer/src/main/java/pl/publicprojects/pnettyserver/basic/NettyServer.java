package pl.publicprojects.pnettyserver.basic;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import pl.publicprojects.pcommon.protocol.decoder.PacketDecoder;
import pl.publicprojects.pcommon.protocol.decoder.SizeDecoder;
import pl.publicprojects.pcommon.protocol.encoder.PacketEncoder;
import pl.publicprojects.pcommon.protocol.encoder.SizeEncoder;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pnettyserver.handler.AbstractHandler;
import pl.publicprojects.pnettyserver.session.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class NettyServer {

    private final PacketUtil packetUtil;
    private boolean started = false;
    private final List<AbstractHandler> handlerList;
    private final int port;

    public NettyServer(int port) {
        this.handlerList = new ArrayList<>();
        this.packetUtil = new PacketUtil();
        this.packetUtil.registerServerPackets();
        this.port = port;
    }

    public void start() {
        try {
            NettyServer nettyServer = this;
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        private final Session session = new Session(nettyServer);

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new SizeDecoder())
                                    .addLast(new PacketDecoder(packetUtil, session))
                                    .addLast(new SizeEncoder())
                                    .addLast(new PacketEncoder());
                        }
                    })
                    .bind(this.port)
                    .sync();
                    System.out.println("Server started! Port: " + this.port);
                    started = true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void registerHandler(AbstractHandler handler) {
        this.handlerList.add(handler);
    }

    public void registerHandlers(AbstractHandler... handlers) {
        Arrays.asList(handlers).forEach(this::registerHandler);
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer(9876);
        server.start();
    }
}