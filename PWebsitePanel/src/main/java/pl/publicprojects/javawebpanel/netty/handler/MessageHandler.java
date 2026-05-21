package pl.publicprojects.javawebpanel.netty.handler;

import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.publicprojects.javawebpanel.websocket.handler.WebSocketSessionHandler;
import pl.publicprojects.pcommon.protocol.packet.Packet;
import pl.publicprojects.pcommon.protocol.packet.packets.clientbound.MessagePacket;
import pl.publicprojects.pnettyclient.basic.NettyClient;
import pl.publicprojects.pnettyclient.handler.AbstractHandler;

/**
 * Listens messages from minecraft server sent through minecraft plugin
 */
@Getter
@Component
public class MessageHandler implements AbstractHandler {

    private final WebSocketSessionHandler webSocketSessionHandler;
    private final NettyClient client;

    public MessageHandler(NettyClient client, WebSocketSessionHandler webSocketSessionHandler) {
        this.client = client;
        this.webSocketSessionHandler = webSocketSessionHandler;
        this.client.getHandlerList().add(this);
    }

    @Override
    public void handle(Packet packet) {
        if(packet instanceof MessagePacket messagePacket) {
            this.webSocketSessionHandler.sendMessage(messagePacket.getMessage());
        }
    }
}
