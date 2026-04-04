package pl.publicprojects.pnettyserver.protocol.packet;

import pl.publicprojects.pnettyserver.protocol.PanelBuffer;

public abstract class Packet implements Cloneable {
    public abstract int getId();
    public abstract void read(PanelBuffer buf);
    public abstract void write(PanelBuffer buf);

    @Override
    public Packet clone() {
        try {
            return (Packet) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
