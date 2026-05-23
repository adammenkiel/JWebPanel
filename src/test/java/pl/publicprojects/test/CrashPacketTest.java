package pl.publicprojects.test;

import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CrashPacketTest {
    @Test
    public void crashPacketTest() throws IOException {
        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());
        for(int i = 0; i < 10; i++) {
            stream.writeInt(3);

            //readInt requires 4 bytes but I send just 3 ones
            stream.writeByte(0);
            stream.writeByte(0);
            stream.writeByte(0);
        }
        while(true) {
            int val = s.getInputStream().read();
            System.out.println( val + " (" + (char)val + ")");
        }
    }
}
