package pl.publicprojects.test.tests.pcommon;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;
import pl.publicprojects.pcommon.protocol.PanelBuffer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PanelBufferTests {

    public void writeString(DataOutputStream stream, String text) throws IOException {
        stream.writeInt(text.getBytes(StandardCharsets.UTF_8).length);
        stream.write(text.getBytes(StandardCharsets.UTF_8));
    }

    public void writeStringList(DataOutputStream stream, List<String> list) throws IOException {
        stream.writeInt(list.size());
        for(String elem : list)
            this.writeString(stream, elem);
    }
    @Test
    public void testReadString() throws IOException {
        //Arrange
        String exampleString = "Hello world";
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(str);
        this.writeString(out, exampleString);
        ByteBuf buf = Unpooled.wrappedBuffer(str.toByteArray());
        PanelBuffer sut = new PanelBuffer(buf);

        //Act
        String resultString = sut.readString();

        //Assert
        assertEquals(resultString, exampleString);
    }

    @Test
    public void testReadStringList() throws IOException {
        //Arrange
        List<String> list = List.of(new String[]{"First", "second"});
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(str);
        this.writeStringList(out, list);
        ByteBuf buf = Unpooled.wrappedBuffer(str.toByteArray());
        PanelBuffer sut = new PanelBuffer(buf);

        //Act
        List<String> resultList = sut.readStringList();

        //Assert
        assertEquals(resultList, list);
    }
}
