package ch.akkatest.chat.server;

import static ch.akkatest.chat.common.ChatUtil.*;

import ch.akkatest.chat.common.*;
import ch.akkatest.chat.common.event.*;

import static akka.actor.Actors.*;

import akka.actor.TypedActor;
import akka.actor.UntypedActor;
import ch.akkatest.chat.proto.ClientMessage;

import java.util.*;

/**
 * @author Sveta Kharlan
 */
public class ChatServer {

    public static void main(String[] args) {
        new ChatServer().start();
    }

    private void start() {
        remote().start(CHAT_SERVICE_HOST, CHAT_SERVICE_PORT, getClass().getClassLoader()).register(CHAT_SERVICE_NAME, actorOf(ChatServerListener.class));
    }

    public static class ChatServerListener extends UntypedActor {

        private static final Map<String, ChatSession> sessions = new HashMap<String, ChatSession>();

        public void onReceive(Object event) {
            if (event instanceof LoginEvent) {
                doLogin((LoginEvent)event);
            } else if (event instanceof MessageEvent) {
                broadcastMessage((MessageEvent)event);
            } else if (event instanceof LogoutEvent) {
                doLogout((LogoutEvent)event);
            } else if (event instanceof GetAllUserNamesEvent) {
               doGetAllUserNames((GetAllUserNamesEvent) event);
            }
        }

        private void doLogin(LoginEvent login) {
            ChatSession session = TypedActor.newRemoteInstance(ChatSession.class, ChatSessionImpl.class, login.getClientHost(), login.getClientPort());
            String username = login.getUsername();
            sessions.put(username, session);
            ChatPrinter.getOutput().println(username + " just logged in");

            broadcastMessage(username, ClientMessage.Message.newBuilder().setMes("I just logged in").build());
        }

        private void broadcastMessage(MessageEvent messageEvent) {
            broadcastMessage(messageEvent.getUsername(), messageEvent.getMessage());
        }

        private void broadcastMessage(String sender, ClientMessage.Message message) {
            ChatPrinter.getOutput().println(sender + " sent: " + message.getMes());
            for (Map.Entry<String, ChatSession> entry : sessions.entrySet()) {
                if (!entry.getKey().equals(sender)) {
                    entry.getValue().printMessage(sender, message);
                }
            }
        }

        private void sendMeMessage(MessageEvent messageEvent) {
            sendMeMessage(messageEvent.getUsername(), messageEvent.getMessage());
        }

        private void sendMeMessage(String sender, ClientMessage.Message message) {
            for (Map.Entry<String, ChatSession> entry : sessions.entrySet()) {
                if (entry.getKey().equals(sender)) {
                    entry.getValue().printMeMessage(sender, message);
                }
            }
        }

        private void doLogout(LogoutEvent logout) {
            String username = logout.getUsername();
            ChatSession session = sessions.remove(username);
            TypedActor.stop(session);
            ChatPrinter.getOutput().println(username + " just logged out");
            broadcastMessage(username, ClientMessage.Message.newBuilder().setMes("I just logged out").build());

        }
        private void doGetAllUserNames(GetAllUserNamesEvent getAllUserNames) {
            String username = getAllUserNames.getUsername();
            Set<String> clientNames =  sessions.keySet();
            StringBuilder sb = new StringBuilder();
            sb.append("You talk with: \n");
            for (String s:clientNames){
                sb.append(s +" ");
            }
            sendMeMessage(username, ClientMessage.Message.newBuilder().setMes(sb.toString()).build());
        }
    }
}
