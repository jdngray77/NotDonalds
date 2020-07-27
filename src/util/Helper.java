package util;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic utility container.
 *
 * Small, miscellaneous functions.
 * @since 0.1.0
 * @version 1
 * @author Jordan Gray
 */
public class Helper {

    //#region logger
    /**
     * Preface text appended to the begining of any log.
     */
    private static String LOG_PREFACE = "[ND]";

    /**
     * List of all parsed logs.
     */
    private static List<String> logs = new ArrayList<>();

    /**
     * Compiles and stores a message from an unknown sender. Parsed latest message to system.out.
     * @param message content of the message
     */
    public static void Log(String message){
        Log("[NULL SENDER]", message);
    }

    /**
     * Compiles and stores a message. Parsed latest message to system.out
     * @param sender Object sending the message
     * @param message content of the message
     */
    public static void Log(Object sender, String message) {
        Log("[" + sender.getClass() + "]", message);
    }

    /**
     * Compiles and stores a message. Parsed latest message to system.out
     * @param sender Class sending the message
     * @param message content of the message
     */
    public static void Log(Class<?> sender, String message) {
        Log((sender == null) ? "[NULL OBJECT]" : sender.getSimpleName(), message);
    }

    /**
     * Compiles and stores a message. Parsed latest message to system.out
     * @param sender Sender of the message. Only use this directly if calling from a static location. Ensure to use '[..]' to maintain conformity.
     * @param message content of the message
     */
    public static void Log(String sender, String message){
        logs.add(LOG_PREFACE + " " +                          // Create and log message
                sender + ": " +
                message
        );
        System.out.println(logs.get(logs.size() - 1));        // Print latest message to out stream
    }
    //#endregion


    //#region runtime

    /**
     * Log and halt the runtime with the parsed code.
     * @param code Standardised local halt code.
     */
    public static void halt(HaltCodes code){
        Log("[HALT] Halting with code " + code.toString());
        Runtime.getRuntime().halt(code.ordinal());
    }
    //#endregion

}
