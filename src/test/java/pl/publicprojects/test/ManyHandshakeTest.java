package pl.publicprojects.test;

import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ManyHandshakeTest {
    /**
     * Tries to send many handshake in just one connection.
     */
    @Test
    public void manyConnect() throws IOException {
        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());
        for(int i = 0; i < 10; i++) {
            stream.writeInt(4);
            stream.writeInt(0);
        }
        while(true) {
            int val = s.getInputStream().read();
            System.out.println( val + " (" + (char)val + ")");
        }
    }
}
