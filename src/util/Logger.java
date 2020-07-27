package util;

public class Logger {

    private static String PREFACE = "[ND]";

    public static void internal(String message){
        internal("[NULL SENDER]", message);
    }

    public static void internal(Object sender, String message) {
        internal(sender.getClass(), message);
    }

    public static void internal(Class<?> sender, String message) {
        internal((sender == null) ? "[NULL OBJECT]" : sender.getSimpleName(), message);
    }


    public static void internal(String sender, String message){
        System.out.println(PREFACE + " " +
                sender + ": " +
                message
                );
    }
}
