package ro.tuc.ds2020.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ro.tuc.ds2020.components.WsChannelManager;
import ro.tuc.ds2020.services.JwtService;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class CustomWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private JwtService jwtService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Extract query parameters
        String query = session.getUri().getQuery();
        if (query == null || !query.contains("token=")) {
            session.close(); // Close the connection if the token is missing
            return;
        }

//         Extract the token
        String token = query.split("token=")[1];
        if (token.contains("&")) {
            token = token.split("&")[0]; // Handle additional query parameters
        }
//
        // Validate the token
        try {
            jwtService.validateToken(token);
        } catch (Exception e) {
            session.close(); // Close the connection if token validation fails
            return;
        }
        String chatId = extractChatIdFromSession(session);
        WsChannelManager.addSession(chatId, session);
        log.info("Connection established "+token+" "+chatId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String chatId = extractChatIdFromSession(session);
        WsChannelManager.removeSession(chatId, session);
        log.info("Connection closed "+chatId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String chatId = extractChatIdFromSession(session);

        WsChannelManager.broadcastMessage(chatId, message);
    }

    private String extractChatIdFromSession(WebSocketSession session) {
        String[] pathSegments = Objects.requireNonNull(session.getUri()).getPath().split("/");
        return pathSegments[pathSegments.length - 1];
    }
}