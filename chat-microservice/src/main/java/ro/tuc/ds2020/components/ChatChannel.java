package ro.tuc.ds2020.components;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

public class ChatChannel {
    @Getter
    private final String chatId;
    private final Set<WebSocketSession> sessions = new HashSet<>();

    public ChatChannel(String chatId) {
        this.chatId = chatId;
    }

    public synchronized void addSession(WebSocketSession session) {
        sessions.add(session);
    }

    public synchronized Set<WebSocketSession> getSessions() {
        return sessions;
    }
}
