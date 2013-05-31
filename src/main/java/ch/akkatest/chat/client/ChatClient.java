package ch.akkatest.chat.client;

import static akka.actor.Actors.*;
import akka.actor.*;

import ch.akkatest.chat.common.event.*;
import ch.akkatest.chat.proto.*;

import static ch.akkatest.chat.common.ChatUtil.*;

/**
 * @author Sveta Kharlan
 */
public class ChatClient {

    private final String username;
    private ActorRef serverActor;

    public ChatClient(String username) {
        this.username = username;
    }

    public void startAndLogin() {
        remote().start(ClientApp.CLIENT_HOST, ClientApp.CLIENT_PORT);
        serverActor = getServerActor();

        login();
    }

    public void chatting(String text) {
        serverActor.tell(new MessageEvent(username, toMessage(text)));
    }

    public void logout() {
        serverActor.tell(new LogoutEvent(username));
        remote().shutdown();
    }

    public void getAllClients(){
        serverActor.tell(new GetAllUserNamesEvent(username));
    }

    private void login() {
        serverActor.tell(new LoginEvent(ClientApp.CLIENT_HOST, ClientApp.CLIENT_PORT, username));

    }

    private ActorRef getServerActor() {
        if (serverActor == null) {
            serverActor = remote().actorFor(CHAT_SERVICE_NAME, CHAT_SERVICE_HOST, CHAT_SERVICE_PORT);
        }

        return serverActor;
    }

    private static ClientMessage.Message toMessage(String text) {
        ClientMessage.Message.Builder mes = ClientMessage.Message.newBuilder();
        mes.setMes(text);

        return mes.build();
    }

    public void setServerActor(ActorRef serverActor) {
        this.serverActor = serverActor;
    }
}
