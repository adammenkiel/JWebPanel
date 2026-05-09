package pl.publicprojects.javawebpanel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import pl.publicprojects.javawebpanel.websocket.handler.WebSocketSessionHandler;
import pl.publicprojects.javawebpanel.websocket.interceptor.WebSocketHandshakeInterceptor;


@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private WebSocketHandshakeInterceptor interceptor;
    @Autowired
    private WebSocketSessionHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.handler, "/ws")
                .addInterceptors(this.interceptor)
                .setAllowedOrigins("*");
    }
}
