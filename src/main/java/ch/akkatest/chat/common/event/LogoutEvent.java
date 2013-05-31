package ch.akkatest.chat.common.event;

import net.jcip.annotations.Immutable;

/**
 */
@Immutable
public class LogoutEvent extends ChatEvent {

    public LogoutEvent(String username) {
        super(username);
    }
}
