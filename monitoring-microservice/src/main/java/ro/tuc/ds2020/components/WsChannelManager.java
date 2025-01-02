package ro.tuc.ds2020.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class WsChannelManager {
    private static final Map<String, ChatChannel> channels = new HashMap<>();

    public static synchronized void addChannel(String chatId) {
        log.info("Starting channel: " + chatId);
        channels.put(chatId, new ChatChannel(chatId));
    }

    private static synchronized void removeChannel(String chatId) {
        channels.remove(chatId);
    }

    public static synchronized void removeSession(String chatId, WebSocketSession session) {
        if (channels.get(chatId) != null) {
            channels.get(chatId).getSessions().remove(session);
        }
    }

    public static Set<WebSocketSession> getSessions(String chatId) {
        return channels.get(chatId).getSessions();
    }

    public static synchronized void addSession(String chatId, WebSocketSession session) throws IOException {
        if (channels.get(chatId) == null) {
            session.close();
        } else {
            channels.get(chatId).addSession(session);
            log.info("Added session to channel: " + chatId);
        }
    }

    private static void closeAllSessionsForChannel(String chatId) {
        for (WebSocketSession session : channels.get(chatId).getSessions()) {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void broadcastMessage(String chatId, TextMessage message) throws IOException {
        ChatChannel channel = channels.get(chatId);
        if (channel != null) {
            for (WebSocketSession sessionInChannel : channel.getSessions()) {
                sessionInChannel.sendMessage(message);
                log.info("Broadcasted message to session {}: {}",sessionInChannel.getId(), message.getPayload());
            }
        }
    }
}

