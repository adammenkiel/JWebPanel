package pl.publicprojects.pcommon.protocol.packet;

import lombok.Getter;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessageGroupPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.serverbound.JoinPacket;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessagePacket;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PacketUtil {

    private final Map<Integer, Packet> packets = new HashMap<>();

    /**
     * Function for register packet
     *
     * @param packet Packet for register
     */
    private void registerPacket(Packet packet) {
        this.packets.put(packet.getId(), packet);
    }

    /**
     * Function for register set of packets
     *
     * @param packets set of packets
     */
    private void registerPackets(Packet... packets) {
        Arrays.asList(packets).forEach(this::registerPacket);
    }

    /**
     * Function for get packet by id
     *
     * @param id Id of packet that we need
     * @return Packet obj
     */
    public Packet getPacketById(int id) {
        return this.packets.get(id).clone();
    }

    /**
    * Packets that the client need to register
    */
    public void registerClientPackets() {
        this.registerPackets(
                new MessageGroupPacket(),
                new MessagePacket()
        );
    }
    /**
     * Packets that the server needs to register
     */
    public void registerServerPackets() {
        this.registerPackets(
                new JoinPacket()
        );
    }
}
