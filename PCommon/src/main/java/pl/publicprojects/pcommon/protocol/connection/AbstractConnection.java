package pl.publicprojects.pcommon.protocol.connection;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.PacketUtil;
import pl.publicprojects.pcommon.protocol.packet.state.PacketState;


/**
 * Abstract class for object that will manage with connection and handle packets
 */
@Setter
@Getter
public abstract class AbstractConnection {

    private PacketState packetState = PacketState.JOIN;

    /**
     * Function for handle packets
     * @param packet Received packet
     */
    public abstract void handle(Packet packet);

    /**
     * Function for receive Channel while connection
     * @param loginObject Channel object
     */
    public abstract void loginConnection(Object loginObject);

    /**
     * Disconnect client from server
     */
    public abstract void disconnect();

    /**
     * Sending packet to other side, full logic is implemented in <code>PacketEncoder</code> and <code>SizeEncoder</code>
     * @param packet Packet for send
     */
    public abstract void sendPacket(Packet packet);

    @Deprecated
    public abstract String getName();

    /**
     * Function for get Channel
     * @return Channel object
     */
    public abstract Channel getChannel();
}
