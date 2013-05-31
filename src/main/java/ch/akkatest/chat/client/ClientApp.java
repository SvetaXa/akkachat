package ch.akkatest.chat.client;

import ch.akkatest.chat.common.ChatPrinter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static ch.akkatest.chat.common.ChatUtil.getFreePort;

/**
 * @author
 */
public class ClientApp {

    public static final String CLIENT_HOST = "localhost";
    public static final int CLIENT_PORT = getFreePort();

    public static void main(String[] args) {
        ChatClient client = new ChatClient(takeUserName());

        client.startAndLogin();
        while(true) {
            String text = readInput();
            if (text.trim().equalsIgnoreCase("bye")) {
                client.logout();
                System.exit(0);
            }
            if (text.trim().equalsIgnoreCase("all")) {
                client.getAllClients();
                continue;
            }
            client.chatting(text);
        }

    }

    private static String takeUserName() {
        ChatPrinter.getOutput().print("Username: ");
        return readInput();
    }

    private static String readInput() {
        BufferedReader input = new BufferedReader(new InputStreamReader(ChatPrinter.getInput()));
        try {
            return input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ":(";
    }
}
