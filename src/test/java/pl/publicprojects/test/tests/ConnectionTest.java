package pl.publicprojects.test.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.publicprojects.pnettyserver.basic.NettyServer;
import pl.publicprojects.pnettyserver.session.Session;
import pl.publicprojects.test.NettyServerManager;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConnectionTest {

    @Test
    public void connectionTest() throws IOException {
        NettyServerManager.runJustOneTime();

        Logger logger = Logger.getLogger("TEST_LOGGER");

        logger.info("Trying to connect...");

        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());

        //Handshake packet sending...:
        logger.info("Sending handshake...");
        stream.writeInt(4); // len
        stream.writeInt(0); // handshake Id
        logger.info("Handshake sent!");

        logger.info("Waiting 1s ...");
        try { Thread.sleep(1000); } catch (Exception ignored) {} // Time for receive packet
        List<Session> sessionList = Session.getSessionList();
        logger.info("Clients online: " + sessionList.size());
        assertFalse(Session.getSessionList().isEmpty());
        s.close();
    }
}
