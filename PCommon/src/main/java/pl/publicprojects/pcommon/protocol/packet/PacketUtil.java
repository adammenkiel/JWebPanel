package pl.publicprojects.pcommon.protocol.packet;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PacketUtil {
    private Map<Integer, Packet> packets = new HashMap<>();

    public void registerPacket(Packet packet) {
        this.packets.put(packet.getId(), packet);
    }
    public void registerPackets(Packet... packets) {
        Arrays.asList(packets).forEach(this::registerPacket);
    }

    public Packet getPacketById(int id) {
        return this.packets.get(id).clone();
    }
}
