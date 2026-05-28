package pl.publicprojects.pnettyserver.basic;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import pl.publicprojects.pcommon.protocol.handler.ExceptionHandler;
import pl.publicprojects.pcommon.protocol.handler.decoder.PacketDecoder;
import pl.publicprojects.pcommon.protocol.handler.decoder.SizeDecoder;
import pl.publicprojects.pcommon.protocol.handler.encoder.PacketEncoder;
import pl.publicprojects.pcommon.protocol.handler.encoder.SizeEncoder;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.PingPacket;
import pl.publicprojects.pnettyserver.handler.AbstractHandler;
import pl.publicprojects.pnettyserver.session.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


/**
 * Class that implements server
 */
@Getter
public class NettyServer {

    private final PacketUtil packetUtil;
    private boolean started = false;
    private final List<AbstractHandler> handlerList = new CopyOnWriteArrayList<>();
    private final int port;
    private final Logger pluginLogger;
    private ScheduledExecutorService pingScheduler;

    /**
     * @param port Port for server bind
     */
    public NettyServer(Logger pluginLogger, int port) {
        this.pluginLogger = pluginLogger;
        this.packetUtil = new PacketUtil();
        this.packetUtil.registerServerPackets();
        this.port = port;
    }

    /**
     * Function for bind server
     */
    public void start() {
        try {
            NettyServer nettyServer = this;
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            Session session = new Session(nettyServer);

                            socketChannel.pipeline()
                                    .addLast(new SizeDecoder())
                                    .addLast(new PacketDecoder(packetUtil, session))
                                    .addLast(new SizeEncoder())
                                    .addLast(new PacketEncoder())
                                    .addLast(new IdleStateHandler(60, 60, 0, TimeUnit.SECONDS))
                                    .addLast(new ExceptionHandler(session));
                        }
                    })
                    .bind(this.port)
                    .sync();
                    this.started = true;
                    this.pluginLogger.info("Server started! Port: " + this.port);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.pingScheduler = Executors.newScheduledThreadPool(1);
        this.pingScheduler.scheduleAtFixedRate(() -> {
            for(Session session : Session.getSessionList()) {
                session.sendPacket(new PingPacket(System.currentTimeMillis()));
            }
        }, 0, 30, TimeUnit.SECONDS);
    }

    public void shutdown() {
        this.started = false;
        this.pingScheduler.close();
    }

    public void registerHandler(AbstractHandler handler) {
        this.handlerList.add(handler);
    }

    public void registerHandlers(AbstractHandler... handlers) {
        Arrays.asList(handlers).forEach(this::registerHandler);
    }
}