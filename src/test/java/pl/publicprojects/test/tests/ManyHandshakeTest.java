package pl.publicprojects.test.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.publicprojects.pnettyserver.session.Session;
import pl.publicprojects.test.NettyServerManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ManyHandshakeTest {
    /**
     * Tries to send many handshake in just one connection.
     */
    @Test
    public void manyConnect() throws IOException {
        NettyServerManager.runJustOneTime();

        Logger logger = Logger.getLogger("TEST_LOGGER");
        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());

        logger.info("Checking clients on the server...");
        int clientsSize = Session.getSessionList().size();
        logger.info("Sending first handshake...");
        stream.writeInt(4);
        stream.writeInt(0);
        try { Thread.sleep(1000); } catch (Exception ignored) {}

        assertTrue(Session.getSessionList().size() > clientsSize);
        logger.info("Client is connected!");
        logger.info("Sending it again at the same connection...");
        stream.writeInt(4);
        stream.writeInt(0);
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        assertEquals(Session.getSessionList().size(), clientsSize);
        logger.info("Connection lost!");
        s.close();

    }
}
