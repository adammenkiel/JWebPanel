package pl.publicprojects.pcommon.protocol.packet.packets.clientbound;

import lombok.Getter;
import pl.publicprojects.pcommon.protocol.PanelBuffer;
import pl.publicprojects.pcommon.protocol.packet.Packet;

import java.util.List;

/**
 * That's packet sends group of messages while client joins to server
 */
@Getter
public class MessageGroupPacket extends Packet {

    private List<String> messages;

    public MessageGroupPacket() {}

    /**
     * @param messages List of chat messages.
     */
    public MessageGroupPacket(List<String> messages) {
        this.messages = messages;
    }

    /**
     * Method returns packet id
     *
     * @return Packet id
     */
    @Override
    public int getId() {
        return 0;
    }

    /**
     * Packet data receiving method
     *
     * @param buf Contains data of packet, reads stringList
     */
    @Override
    public void read(PanelBuffer buf) {
        this.messages = buf.readStringList();
    }

    /**
     * Packet data sending method
     *
     * @param buf Buffer for write packet data, here we writes messages stringList
     * */
    @Override
    public void write(PanelBuffer buf) {
        buf.writeStringList(this.messages);
    }
}
