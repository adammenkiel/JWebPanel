package pl.publicprojects.pcommon.protocol.connection;

import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;

public abstract class AbstractConnection {
    public abstract void handle(Packet packet);
    public abstract void loginConnection(Object loginObject);
    public abstract void disconnect();
    public abstract void sendPacket(Packet packet);
    public abstract String getName();
}
