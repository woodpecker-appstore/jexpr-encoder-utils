package me.gv7.woodpecker.plugin.utils;

public class Utils {
    public static String escape(String payload) {
        return payload.replace("'", "\\'");
    }

    public static String escape(String payload, String c) {
        return payload.replace(c, "\\" + c);
    }
}
