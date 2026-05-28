package pl.publicprojects.pcommon.protocol.packet.packets.clientbound;

import lombok.Getter;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

@Getter
public class PingPacket extends Packet {

    private long time;

    public PingPacket() {}

    public PingPacket(long time) {
        this.time = time;
    }

    @Override
    public int getId() {
        return 3;
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
