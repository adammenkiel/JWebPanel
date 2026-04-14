package pl.publicprojects.pnettyserver.session;

import io.netty.channel.ChannelHandlerContext;
import lombok.Getter;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;

import java.util.ArrayList;
import java.util.List;

public class Session extends AbstractConnection {

    private ChannelHandlerContext channelHandlerContext;
    @Getter
    private static final List<Session> sessionList = new ArrayList<>();

    @Override
    public void loginConnection(Object loginObject) {
        if(loginObject instanceof ChannelHandlerContext context) {
            this.channelHandlerContext = context;
        }
    }

    @Override
    public void disconnect() {
        channelHandlerContext.disconnect();
    }

    @Override
    public void handle(Packet packet) {
        //Maybe I should do separated function for it
        if(packet instanceof JoinPacket) {
            sessionList.add(this);
            System.out.println("Registered new connection!");
        }
    }
}
