package pl.publicprojects.pnettyserver.basic;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import pl.publicprojects.pnettyserver.protocol.decoder.PacketDecoder;
import pl.publicprojects.pnettyserver.protocol.decoder.SizeDecoder;
import pl.publicprojects.pnettyserver.protocol.packet.PacketUtil;

public class NettyServer {

    private final PacketUtil packetUtil;

    public NettyServer() {
        this.packetUtil = new PacketUtil();
    }

    private void start() {
        try {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new SizeDecoder());
                            socketChannel.pipeline().addLast(new PacketDecoder(packetUtil));

                        }
                    })
                    .bind()
                    .sync();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}