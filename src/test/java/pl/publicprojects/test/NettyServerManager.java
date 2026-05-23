package pl.publicprojects.test;

import lombok.Getter;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import pl.publicprojects.pnettyserver.basic.NettyServer;

import java.util.logging.Logger;

public class NettyServerManager {

    @Getter
    private static NettyServer server;
    private static boolean started = false;

    public static void runJustOneTime() {
        if(started) return;
        started = true;

        Logger logger = Logger.getLogger("NETTY_SERVER_LOGGER");
        logger.info("Binding port...");
        server = new NettyServer(logger, 9876);
        server.start();
        logger.info("Server started!");
    }
}
