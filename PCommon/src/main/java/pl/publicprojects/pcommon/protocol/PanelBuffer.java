package pl.publicprojects.pcommon.protocol;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

public record PanelBuffer(ByteBuf byteBuf) {

    public String readString() {
        int len = byteBuf.readInt();
        return new String(byteBuf.readBytes(len).array());
    }

}
