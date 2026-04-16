package pl.publicprojects.pcommon.protocol;

import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/*
* Simple record for simplify read and write functions.
*/
public record PanelBuffer(ByteBuf byteBuf) {

    public String readString() {
        byte[] bytes = new byte[byteBuf.readInt()];
        byteBuf.readBytes(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void writeString(String str) {
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        byteBuf.writeInt(strBytes.length);
        byteBuf.writeBytes(strBytes);
    }

    public List<String> readStringList() {
        List<String> list = new ArrayList<>();
        int size = this.byteBuf.readInt();
        for(int i = 0; i < size; i++) {
            list.add(this.readString());
        }
        return list;
    }

    public void writeStringList(List<String> list) {
        this.byteBuf.writeInt(list.size());
        for(String str : list) {
            this.writeString(str);
        }
    }

}
