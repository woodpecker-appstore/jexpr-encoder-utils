package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;

public class FreeMarkerExpr implements IExpr {
    @Override
    public String getName() {
        return "FreeMarker";
    }

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
