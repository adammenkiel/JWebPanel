package pl.publicprojects.test;

import org.junit.jupiter.api.Test;
import pl.publicprojects.pnettyserver.basic.NettyServer;
import pl.publicprojects.pnettyserver.session.Session;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * For correct...
 */
public class ConnectionTest {

    private NettyServer server;

    @Test
    public void connectionTest() throws IOException {
        Logger logger = Logger.getLogger("TEST_LOGGER");
        this.server = new NettyServer(logger, 9876);
        this.server.start();

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
