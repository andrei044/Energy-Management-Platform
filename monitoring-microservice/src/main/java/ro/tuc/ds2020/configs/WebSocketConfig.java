package ro.tuc.ds2020.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ro.tuc.ds2020.components.CustomHandshakeInterceptor;
import ro.tuc.ds2020.services.JwtService;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private CustomWebSocketHandler customWebSocketHandler;
    @Autowired
    private CustomHandshakeInterceptor customHandshakeInterceptor;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Register a default handler, actual endpoints will be handled dynamically
        registry.addHandler(customWebSocketHandler, "/ws/**").setAllowedOrigins("*").addInterceptors(customHandshakeInterceptor);
    }
}