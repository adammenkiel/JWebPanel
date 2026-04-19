package pl.publicprojects.pnettyserver.session;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;
import pl.publicprojects.pcommon.protocol.packet.state.PacketState;
import pl.publicprojects.pnettyserver.basic.NettyServer;
import pl.publicprojects.pnettyserver.handler.AbstractHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Session extends AbstractConnection {

    private Channel channel;
    private final NettyServer nettyServer;

    public Session(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Getter
    private static final List<Session> sessionList = new CopyOnWriteArrayList<>();

    @Override
    public void loginConnection(Object loginObject) {
        if(loginObject instanceof Channel channel) {
            this.channel = channel;
        }
    }

    @Override
    public void disconnect() {
        sessionList.remove(this);
        this.channel.disconnect();
    }

    @Override
    public void sendPacket(Packet packet) {
        this.channel.writeAndFlush(packet).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }

    @Override
    public String getName() {
        return "Session";
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public void handle(Packet packet) {
        for(AbstractHandler handler : this.nettyServer.getHandlerList()) {
            handler.handle(this, packet);
        }

        //Maybe I should do separated function for it
        if(packet instanceof JoinPacket) {
            if(this.getPacketState() != PacketState.JOIN) {
                System.out.println("Something strange happened");
                this.disconnect();
                return;
            }
            sessionList.add(this);
            this.setPacketState(PacketState.CONNECTED);
            System.out.println("Registered new connection!");
        }
    }


}
