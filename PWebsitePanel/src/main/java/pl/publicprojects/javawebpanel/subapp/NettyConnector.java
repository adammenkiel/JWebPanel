package pl.publicprojects.javawebpanel.subapp;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.publicprojects.pnettyclient.basic.NettyClient;

@Component
public class NettyConnector {

    private final NettyClient client;

    public NettyConnector(NettyClient client) {
        this.client = client;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        new Thread(() -> client.connect("localhost", 9876)).start();
    }
}
