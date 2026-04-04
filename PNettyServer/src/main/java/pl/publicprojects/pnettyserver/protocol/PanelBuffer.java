package pl.publicprojects.pnettyserver.protocol;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

@Getter
public record PanelBuffer(ByteBuf byteBuf) {

    public String readString() {
        int len = byteBuf.readInt();
        return new String(byteBuf.readBytes(len).array());
    }
}
