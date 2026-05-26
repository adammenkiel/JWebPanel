package pl.publicprojects.pcommon.protocol.packet.packets.clientbound;

import lombok.Getter;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

import java.util.List;
@Getter
public class DisconnectPacket extends Packet {

    private String message;

    public DisconnectPacket() {}

    public DisconnectPacket(String message) {
        this.message = message;
    }

    @Override
    public int getId() {
        return 2;
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
