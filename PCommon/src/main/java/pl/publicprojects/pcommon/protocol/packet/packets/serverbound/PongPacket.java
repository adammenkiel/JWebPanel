package pl.publicprojects.pcommon.protocol.packet.packets.serverbound;

import lombok.Getter;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

@Getter
public class PongPacket extends Packet {

    private long time;

    public PongPacket() {}

    public PongPacket(long time) {
        this.time = time;
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void read(PanelBuffer buf) {
        this.time = buf.byteBuf().readLong();
    }

    @Override
    public void write(PanelBuffer buf) {
        buf.byteBuf().writeLong(this.time);
    }
}
