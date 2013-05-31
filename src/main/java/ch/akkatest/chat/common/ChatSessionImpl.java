package ch.akkatest.chat.common;

import ch.akkatest.chat.proto.*;

/**
 * @author Sveta Kharlan
 */
public class ChatSessionImpl extends akka.actor.TypedActor implements ChatSession {

    public void printMessage(String sender, ClientMessage.Message message) {
        ChatPrinter.getOutput().println(sender + ": " + message.getMes());
    }

    public void printMeMessage(String sender, ClientMessage.Message message) {
        ChatPrinter.getOutput().println(message.getMes());
    }
}
