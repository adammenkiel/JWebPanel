package pl.publicprojects.pcommon.protocol.packet.packets.clientbound;

import lombok.Getter;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

@Getter
public class MessagePacket extends Packet {

    private String message;

    public MessagePacket() {}

    public MessagePacket(String message) {
        this.message = message;
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void read(PanelBuffer buf) {
        this.message = buf.readString();
    }

    @Override
    public void write(PanelBuffer buf) {
        buf.writeString(this.message);
    }
}
