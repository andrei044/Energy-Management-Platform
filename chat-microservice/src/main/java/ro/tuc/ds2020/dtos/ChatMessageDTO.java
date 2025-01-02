package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private String content;
    private String sender;
    private String receiver;
    private Action action;
    public enum Action {
        SEEN("ACTION_SEEN"),
        MESSAGE("ACTION_MESSAGE"),
        TYPING("ACTION_TYPING");
        Action(String action_seen) {
        }
    }
}
