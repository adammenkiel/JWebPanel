package pl.publicprojects.pnettyserver.packet;

import io.netty.buffer.ByteBuf;

@Ge
public class PanelBuffer {

    private ByteBuf byteBuf;

    public PanelBuffer(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }
}
