package ro.tuc.ds2020.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ro.tuc.ds2020.components.ChatChannel;
import ro.tuc.ds2020.components.WsChannelManager;
import ro.tuc.ds2020.dtos.ChatMessageDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
@Slf4j
@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private JwtService jwtService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        String chatId = extractChatIdFromSession(session);
        String query = Objects.requireNonNull(session.getUri()).getQuery();
        String token = query.split("token=")[1];
        if (token.contains("&")) {
            token = token.split("&")[0]; // Handle additional query parameters
        }
        try{
            UserDTO userDTO = jwtService.validateToken(token);
            if(isAdmin(userDTO)){
                WsChannelManager.addAdminSession(session);
                WsChannelManager.getChannels().forEach(chatChannel -> {
                    try {
                        WsChannelManager.addSession(chatChannel.getChatId(), session);
                    } catch (IOException e) {
//                        throw new RuntimeException(e);
                        log.warn("ADMIN Error adding session to channel: " + chatChannel.getChatId());
                    }
                });
            }else{
                WsChannelManager.addChannel(userDTO.getName());
                WsChannelManager.addSession(userDTO.getName(), session);
                WsChannelManager.addAdminsToChannel(userDTO.getName());
            }
        } catch (Exception e) {
            session.close();
        }


//        WsChannelManager.addSession(chatId, session);
    }

    private static boolean isAdmin(UserDTO userDTO) {
        return userDTO.getRole().equals(UserDTO.RoleDTO.ROLE_ADMIN);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String chatId = extractChatIdFromSession(session);
        WsChannelManager.removeSession(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String chatId = extractChatIdFromSession(session);


        if(chatId.equals("admin")){
            String payload = message.getPayload();
            // Parse the payload to ChatMessageDTO
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                ChatMessageDTO chatMessage = objectMapper.readValue(payload, ChatMessageDTO.class);

                // Extract receiver and broadcast message
                String receiver = chatMessage.getReceiver();
                WsChannelManager.broadcastMessage(receiver, new TextMessage(payload));
            } catch (IOException e) {
                // Handle invalid JSON or parsing issues
                log.warn("Failed to parse message payload: " + e.getMessage());
//                throw e;
            }
        }else{
            try{
                WsChannelManager.broadcastMessage(chatId, message);
            }catch (Exception e){
                log.warn("Failed to broadcast message to channel {}: {}", chatId, e.getMessage());
            }

        }
    }

    private String extractChatIdFromSession(WebSocketSession session) {
        String[] pathSegments = Objects.requireNonNull(session.getUri()).getPath().split("/");
        return pathSegments[pathSegments.length - 1];
    }
}