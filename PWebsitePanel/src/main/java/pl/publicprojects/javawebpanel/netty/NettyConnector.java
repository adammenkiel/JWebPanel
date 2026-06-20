package pl.publicprojects.javawebpanel.netty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.publicprojects.pnettyclient.basic.NettyClient;

@Component
public class NettyConnector {


    @Value(value = "${public-projects.web-panel.plugin-host}")
    private String host;
    @Value(value = "${public-projects.web-panel.plugin-port}")
    private int port;
    private final NettyClient client;

    public NettyConnector(NettyClient client) {
        this.client = client;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        new Thread(() -> client.connect(this.host, this.port)).start();
    }
}
