package pl.publicprojects.pcommon.protocol.packet.packets.serverbound;

import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

import java.util.List;

public class InfoPackPacket extends Packet {

    private List<String> messages;

    public InfoPackPacket() {}

    public InfoPackPacket(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void read(PanelBuffer buf) {
        this.messages = buf.readStringList();
    }

    @Override
    public void write(PanelBuffer buf) {
        buf.writeStringList(this.messages);
    }
}
