package ch.akkatest.chat.common.event;

import ch.akkatest.chat.proto.ClientMessage;
import net.jcip.annotations.Immutable;

/**
 * @author Sveta Kharlan
 */
@Immutable
public class MessageEvent extends ChatEvent {

    private final ClientMessage.Message message;

    public MessageEvent(String username, ClientMessage.Message message) {
        super(username);
        this.message = message;
    }

    public ClientMessage.Message getMessage() {
        return message;
    }
}
