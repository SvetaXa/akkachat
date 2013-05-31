package ch.akkatest.chat.common;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author Sveta Kharlan
 */
public class ChatPrinter {

    public static PrintStream getOutput() {
        return System.out;
    }

    public static InputStream getInput() {
        return System.in;
    }
}
              