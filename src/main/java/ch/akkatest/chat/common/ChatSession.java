package ch.akkatest.chat.common;

import ch.akkatest.chat.proto.ClientMessage;

/**
 * @author Sveta Kharlan
 *
 */
public interface ChatSession {

    void printMessage(String sender, ClientMessage.Message message);
    void printMeMessage(String sender, ClientMessage.Message message);
}
