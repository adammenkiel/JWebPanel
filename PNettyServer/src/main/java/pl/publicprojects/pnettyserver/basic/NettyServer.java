package pl.publicprojects.pnettyserver.basic;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.Getter;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.decoder.PacketDecoder;
import pl.publicprojects.pcommon.protocol.decoder.SizeDecoder;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.InfoPackPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;
import pl.publicprojects.pnettyserver.session.Session;

@Getter
public class NettyServer {

    private final PacketUtil packetUtil;

    public NettyServer() {
        this.packetUtil = new PacketUtil();
        this.packetUtil.registerServerPackets();
    }

    private void start() {
        try {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        private final Session session = new Session();

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new SizeDecoder());
                            socketChannel.pipeline().addLast(new PacketDecoder(packetUtil, session));
                        }
                    })
                    .bind(9876)
                    .sync();
                    System.out.println("Server started! Port: 9876");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        NettyServer server = new NettyServer();
        server.start();
    }
}