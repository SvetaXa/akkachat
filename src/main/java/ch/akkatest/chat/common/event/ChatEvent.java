package ch.akkatest.chat.common.event;

import net.jcip.annotations.Immutable;

import java.io.*;

/**
 * @author Sveta Kharlan
 */
@Immutable
public abstract class ChatEvent implements Serializable {

    private final String username;

    public ChatEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
