package pl.publicprojects.pcommon.protocol.packet.packets.serverbound;

import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

public class JoinPacket extends Packet {
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void read(PanelBuffer buf) {

    }

    @Override
    public void write(PanelBuffer buf) {

    }
}
