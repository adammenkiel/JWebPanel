package pl.publicprojects.javawebpanel.websocket.handler;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Component
public class WebSocketSessionHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.sessionList.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        this.sessionList.remove(session);
    }

    public void sendMessage(String message) {
        this.sessionList.forEach(session -> {
            try {
                session.sendMessage(new TextMessage(message.getBytes(StandardCharsets.UTF_8)));
            } catch (IOException e) {
                try {session.close();} catch (IOException ignored) {}
                this.sessionList.remove(session);
            }
        });
    }
}
