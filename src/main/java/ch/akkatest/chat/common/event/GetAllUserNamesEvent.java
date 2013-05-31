package ch.akkatest.chat.common.event;

import net.jcip.annotations.Immutable;

/**
 */
@Immutable
public class GetAllUserNamesEvent extends ChatEvent {

    public GetAllUserNamesEvent(String username) {
        super(username);
    }

}
