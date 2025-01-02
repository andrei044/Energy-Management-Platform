package ro.tuc.ds2020.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.*;

@Slf4j
public class WsChannelManager {
    private static final Map<String, ChatChannel> channels = new HashMap<>();
    private static final Set<WebSocketSession> adminSessions = new HashSet<>();

    public static synchronized void addChannel(String chatId) {
        log.info("Starting channel: " + chatId);

        ChatChannel chatChannel = channels.get(chatId);
        if(chatChannel==null){
            channels.put(chatId, new ChatChannel(chatId));
        }else{
            log.warn("Channel already exists: " + chatId);
        }


    }

    private static synchronized void removeChannel(String chatId) {
        channels.remove(chatId);
    }

    public static synchronized void removeSession(WebSocketSession session) {
        channels.forEach((chatId, chatChannel) -> {
            chatChannel.getSessions().remove(session);
            if (chatChannel.getSessions().isEmpty()) {
                removeChannel(chatId);
            }
        });
        adminSessions.remove(session);
//        if (channels.get(chatId) != null) {
//            channels.get(chatId).getSessions().remove(session);
//        }
    }

    public static Set<WebSocketSession> getSessions(String chatId) {
        return channels.get(chatId).getSessions();
    }

    public static synchronized void addSession(String chatId, WebSocketSession session) throws IOException {
        if (channels.get(chatId) == null) {
            session.close();
        } else {
            //TODO check if session is already in channel
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
                if(!sessionInChannel.isOpen()){
                    log.warn("Session {} is closed", sessionInChannel.getId());
                    channel.getSessions().remove(sessionInChannel);
                    continue;
                }try{
                    sessionInChannel.sendMessage(message);
                    log.info("Broadcasted message to session {}: {}", sessionInChannel.getId(), message.getPayload());
                }catch (Exception e) {
                    log.warn("Failed to broadcast message to session {}: {}", sessionInChannel.getId(), e.getMessage());
                    channel.getSessions().remove(sessionInChannel);
                }

            }
        }
    }

    public static List<ChatChannel> getChannels() {
        return new ArrayList<>(channels.values());
    }

    public static void addAdminsToChannel(String name) {
        channels.get(name).getSessions().addAll(adminSessions);
    }
    public static void addAdminSession(WebSocketSession session) {
        adminSessions.add(session);
    }
}

