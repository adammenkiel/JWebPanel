package pl.publicprojects.pcommon.protocol.packet;

import pl.publicprojects.pcommon.protocol.PanelBuffer;

/**
* Abstract class for packets
* Packet structure is composed of
* 1. <code>packet length</code> (total length of bytes table without bytes of size) expressed as integer (4 bytes)
* 2. <code>packet id</code> (we can be distinct packets by reading id from bytes table) expressed as integer (4 bytes)
* 3. <code>data of packet</code> expressed as byte table with length of packet length minus 4 bytes of packet id bytes
* Packets are stored and managed in <code>PacketUtil</code>
*/
public abstract class Packet implements Cloneable {

    /**
    * Unique id is necessary for packet recognitions.
    */
    public abstract int getId();

    /**
    * Receiving data of packet
    *
    * @param buf Buffer of packet data that we can read
    */
    public abstract void read(PanelBuffer buf);

    /**
    * Send data to ByteBuf for packet sending.
    *
    * @param buf Buffer for sending data
    */
    public abstract void write(PanelBuffer buf);

    /**
    * We need to clone packets because sessions works asynchronously so we need many instances of these objects.
    *
    * @return Returns clone of specific packet.
    */
    @Override
    public Packet clone() {
        try {
            return (Packet) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
