package pl.publicprojects.pcommon.protocol.packet.packets.clientbound;

import lombok.Getter;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

import java.util.List;

@Getter
public class MessageGroupPacket extends Packet {

    private List<String> messages;

    public MessageGroupPacket() {}

    public MessageGroupPacket(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public int getId() {
        return 0;
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
