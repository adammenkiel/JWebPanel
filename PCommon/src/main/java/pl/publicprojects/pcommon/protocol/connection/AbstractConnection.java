package pl.publicprojects.pcommon.protocol.connection;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pcommon.protocol.packet.state.PacketState;

@Setter
@Getter
public abstract class AbstractConnection {

    private PacketState packetState = PacketState.JOIN;

    public abstract void handle(Packet packet);
    public abstract void loginConnection(Object loginObject);
    public abstract void disconnect();
    public abstract void sendPacket(Packet packet);
    public abstract String getName();
    public abstract Channel getChannel();
}
