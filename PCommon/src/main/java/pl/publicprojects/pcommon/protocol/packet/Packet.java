package pl.publicprojects.pcommon.protocol.packet;

import pl.publicprojects.pcommon.protocol.PanelBuffer;

/*
* Abstract class for packets
*/
public abstract class Packet implements Cloneable {

    /*
    * Unique id is necessary for packet recognitions.
    */
    public abstract int getId();

    /*
    * Read data from outside. (Receiving the packet)
    */
    public abstract void read(PanelBuffer buf);

    /*
    * Send data to ByteBuf for packet sending.
    */
    public abstract void write(PanelBuffer buf);

    /*
    * We need to clone packets because sessions works asynchronously.
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
