package pl.publicprojects.pnettyclient.basic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.Getter;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.decoder.PacketDecoder;
import pl.publicprojects.pcommon.protocol.decoder.SizeDecoder;
import pl.publicprojects.pcommon.protocol.encoder.PacketEncoder;
import pl.publicprojects.pcommon.protocol.encoder.SizeEncoder;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;

@Getter
public class NettyClient extends AbstractConnection {

    private String host;
    private int port;
    private final PacketUtil packetUtil;
    private ChannelHandlerContext channelHandlerContext;

    public NettyClient() {
        this.packetUtil = new PacketUtil();
        this.packetUtil.registerClientPackets();
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
            future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            group.shutdownGracefully();
        }
    }

    @Override
    public void handle(Packet packet) {
        
    }

    @Override
    public void loginConnection(Object loginObject) {
        if(loginObject instanceof ChannelHandlerContext context) {
            this.channelHandlerContext = context;
        }
    }

    @Override
    public void disconnect() {
        this.channelHandlerContext.disconnect();
    }

    @Override
    public void sendPacket(Packet packet) {
        if(this.channelHandlerContext == null) return;
        this.channelHandlerContext.writeAndFlush(packet);
    }
}
