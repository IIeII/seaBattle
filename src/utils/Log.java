package utils;

public class Log {
    private static boolean isDebugEnabled = true;

    public static void debug(String message) {
        if (isDebugEnabled){
            System.out.println(message);
        }
    }

    public static void error(String message) {
        System.err.println(message);
    }
}
