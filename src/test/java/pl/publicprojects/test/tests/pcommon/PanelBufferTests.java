package pl.publicprojects.test.tests.pcommon;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.jupiter.api.Test;
import pl.publicprojects.pcommon.protocol.PanelBuffer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PanelBufferTests {
    @Test
    public void testReadString() throws IOException {
        //Arrange
        String exampleString = "Hello world";
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(str);
        out.writeInt(exampleString.getBytes(StandardCharsets.UTF_8).length);
        out.write(exampleString.getBytes(StandardCharsets.UTF_8));
        ByteBuf buf = Unpooled.wrappedBuffer(str.toByteArray());
        PanelBuffer sut = new PanelBuffer(buf);

        //Act
        String resultString = sut.readString();

        //Assert
        assertEquals(resultString, exampleString);
    }
}
