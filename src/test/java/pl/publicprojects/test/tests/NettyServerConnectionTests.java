package pl.publicprojects.test.tests;

import org.junit.jupiter.api.Test;
import pl.publicprojects.pnettyserver.session.Session;
import pl.publicprojects.test.NettyServerManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class NettyServerConnectionTests {

    private final Logger logger = Logger.getLogger("TEST_LOGGER");

    public void sendCorrectHandshake(DataOutputStream output) throws IOException {
        output.writeInt(4); // len
        output.writeInt(0); // handshake packet id
    }

    public void sendWrongHandshake(DataOutputStream output) throws IOException {
        output.writeInt(3); // correct packet length
        //readInt requires 4 bytes but I send just 3 ones
        output.writeByte(0);
        output.writeByte(0);
        output.writeByte(0);
    }

    public Socket createCorrectConnection() throws IOException, InterruptedException {
        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());
        this.sendCorrectHandshake(stream);
        Thread.sleep(1000); // to correct
        return s;
    }

    public Socket createWrongConnection() throws IOException {
        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());
        this.sendWrongHandshake(stream);
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        return s;
    }

    @Test
    public void correctConnectionTest() throws IOException, InterruptedException {
        //Arrange
        NettyServerManager.runJustOneTime();
        Socket s = createCorrectConnection();

        //Act
        List<Session> sessionList = Session.getSessionList();

        //Assert
        assertFalse(sessionList.isEmpty());

        //After
        s.close();
    }

    @Test
    public void wrongConnectionTest() throws IOException, InterruptedException {
        //Arrange
        NettyServerManager.runJustOneTime();
        Socket s = createWrongConnection();

        //Act
        List<Session> sessionList = Session.getSessionList();

        //Assert
        assertTrue(sessionList.isEmpty()); // to correct, we need to check something else

        //After
        s.close();
    }

    @Test
    public void doubledHandshakeConnectionTest() throws IOException, InterruptedException {
        //Arrange
        NettyServerManager.runJustOneTime();
        Socket s = createCorrectConnection();

        //Act
        int size = Session.getSessionList().size();

        //Arrange
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        this.sendCorrectHandshake(dos); // handshake should be sent just one time per socket
        Thread.sleep(1000);

        //Act
        int newSize = Session.getSessionList().size();

        //Assert
        assertTrue(size > 0);
        assertEquals(0, newSize);

        //After
        s.close();
    }
}
