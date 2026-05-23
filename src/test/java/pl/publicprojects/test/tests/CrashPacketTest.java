package pl.publicprojects.test.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.publicprojects.test.NettyServerManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CrashPacketTest {
    @Test
    public void crashPacketTest() throws IOException {
        NettyServerManager.runJustOneTime();

        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());
        stream.writeInt(3); // correct packet length
        //readInt requires 4 bytes but I send just 3 ones
        stream.writeByte(0);
        stream.writeByte(0);
        stream.writeByte(0);

        try { Thread.sleep(1000); } catch (Exception ignored) {}

        s.close();
    }
}
