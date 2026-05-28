package pl.publicprojects.helpful.tools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionHelper {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("TEST_LOGGER");

        logger.info("Trying to connect...");

        Socket s = new Socket("localhost", 9876);
        DataOutputStream stream = new DataOutputStream(s.getOutputStream());

        //Handshake packet sending...:
        logger.info("Sending handshake...");
        stream.writeInt(4); // len
        stream.writeInt(0); // handshake Id
        logger.info("Handshake sent!");

        while(true) {}
    }
}
