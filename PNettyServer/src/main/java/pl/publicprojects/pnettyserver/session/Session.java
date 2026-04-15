package pl.publicprojects.pnettyserver.session;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;
import pl.publicprojects.pnettyserver.basic.NettyServer;
import pl.publicprojects.pnettyserver.handler.AbstractHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Session extends AbstractConnection {

    private ChannelHandlerContext channelHandlerContext;
    private final NettyServer nettyServer;

    public Session(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Getter
    private static final List<Session> sessionList = new CopyOnWriteArrayList<>();

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
        this.channelHandlerContext.writeAndFlush(packet);
    }

    @Override
    public void handle(Packet packet) {
        for(AbstractHandler handler : this.nettyServer.getHandlerList()) {
            handler.handle(this, packet);
        }

        //Maybe I should do separated function for it
        if(packet instanceof JoinPacket) {
            sessionList.add(this);
            System.out.println("Registered new connection!");
        }
    }


}
