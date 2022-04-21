package me.gv7.woodpecker.plugin.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

public class Utils {
    public static String escape(String payload) {
        return payload.replace("'", "\\'");
    }

    public static String escape(String payload, String c) {
        return payload.replace(c, "\\" + c);
    }

    public static String getFullClassName(byte[] classFile) throws Exception {
        Method method = ClassLoader.class.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
        method.setAccessible(true);
        Class clazz = (Class) method.invoke(new javax.management.loading.MLet(new URL[0], Thread.currentThread().getContextClassLoader()), classFile, 0, classFile.length);
        return clazz.getName();
    }

    public static String escapeJavaStyleString(String str) throws IOException {
        if (str == null) {
            return null;
        } else {
            StringWriter writer = new StringWriter(str.length() * 2);
            escapeJavaStyleString(writer, str);
            return writer.toString();
        }
    }

    public static void escapeJavaStyleString(Writer out, String str) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Writer must not be null");
        } else if (str != null) {
            int sz = str.length();
            for (int i = 0; i < sz; ++i) {
                char ch = str.charAt(i);
                if (ch > 4095) {
                    out.write("\\u" + hex(ch));
                } else if (ch > 255) {
                    out.write("\\u0" + hex(ch));
                } else if (ch > 127) {
                    out.write("\\u00" + hex(ch));
                } else if (ch < ' ') {
                    if (ch > 15) {
                        out.write("\\u00" + hex(ch));
                    } else {
                        out.write("\\u000" + hex(ch));
                    }
                } else {
                    out.write("\\u00" + hex(ch));
                }
            }

        }
    }

    private static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase(Locale.ENGLISH);
    }
}
