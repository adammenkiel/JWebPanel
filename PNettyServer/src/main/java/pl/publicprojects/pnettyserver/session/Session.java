package pl.publicprojects.pnettyserver.session;

import io.netty.channel.ChannelHandlerContext;
import pl.publicprojects.pcommon.protocol.connection.AbstractConnection;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;

public class Session extends AbstractConnection {

    private ChannelHandlerContext channelHandlerContext;

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
        if(packet instanceof JoinPacket) {
            System.out.println("Registered new connection! ");
        }
    }
}
