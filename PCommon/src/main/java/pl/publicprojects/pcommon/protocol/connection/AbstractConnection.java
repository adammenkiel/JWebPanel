package pl.publicprojects.pcommon.protocol.connection;

import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;

public abstract class AbstractConnection {
    public abstract PacketUtil getPacketUtil();
    public abstract void handle(Packet packet);
}
