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

    private final Logger logger = Logger.getLogger("TEST_LOGGER");

    public void sendHandshake(DataOutputStream output) throws IOException {
        output.writeInt(4); // len
        output.writeInt(0); // handshake packet id
    }
    public Socket createConnection() throws IOException, InterruptedException {
        this.logger.info("Trying to connect...");
        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());
        //Handshake packet sending...:
        this.logger.info("Sending handshake...");
        this.sendHandshake(stream);
        this.logger.info("Handshake sent!");
        this.logger.info("Waiting 1s ...");
        Thread.sleep(1000); // to correct
        return s;
    }

    @Test
    public void connectionTest() throws IOException, InterruptedException {
        //Arrange
        NettyServerManager.runJustOneTime();
        Socket s = createConnection();

        //Act
        List<Session> sessionList = Session.getSessionList();

        //Assert
        assertFalse(sessionList.isEmpty());

        //After
        s.close();
    }
}
