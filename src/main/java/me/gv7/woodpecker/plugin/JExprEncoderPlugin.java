package me.gv7.woodpecker.plugin;

import me.gv7.woodpecker.plugin.exprs.*;
import me.gv7.woodpecker.plugin.utils.WPSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JExprEncoderPlugin implements IHelperPlugin {
    public static IHelperPluginCallbacks callbacks;
    public static IPluginHelper pluginHelper;

    @Override
    public void HelperPluginMain(IHelperPluginCallbacks helperPluginCallbacks) {
        this.callbacks = helperPluginCallbacks;
        this.pluginHelper = helperPluginCallbacks.getPluginHelper();
        helperPluginCallbacks.setHelperPluginAutor("whwlsfb");
        helperPluginCallbacks.setHelperPluginName("JExpr Encoder Utils");
        helperPluginCallbacks.setHelperPluginVersion("0.2.0");
        helperPluginCallbacks.setHelperPluginDescription("Java 表达式注入生成器");
        helperPluginCallbacks.registerHelper(new ArrayList<IHelper>() {{
            add(new JExprEncoder(new SpELExpr()));
            add(new JExprEncoder(new OGNLExpr()));
            add(new JExprEncoder(new FreeMarkerExpr()));
            add(new JExprEncoder(new ELExpr()));
            add(new JExprEncoder(new VelocityExpr()));
            add(new JExprEncoder(new ScriptEngineManagerExpr()));
        }});
    }

    public class JExprEncoder implements IHelper {
        private IExpr _expr = null;

        public JExprEncoder(IExpr expr) {
            _expr = expr;
        }

        @Override
        public String getHelperTabCaption() {
            return _expr.getName();
        }

        @Override
        public IArgsUsageBinder getHelperCutomArgs() {
            IArgsUsageBinder argsUsageBinder = pluginHelper.createArgsUsageBinder();
            List<IArg> args = new ArrayList<>();

            IArg args1 = pluginHelper.createArg();
            args1.setName("command");
            args1.setDefaultValue("whoami");
            args1.setDescription("想要执行的命令");
            args1.setRequired(true);
            args.add(args1);

            IArg args2 = pluginHelper.createArg();
            args2.setName("dnslog_domain");
            args2.setDefaultValue("test.dnslog.com");
            args2.setDescription("dnslog域名");
            args2.setRequired(true);
            args.add(args2);

            IArg args3 = pluginHelper.createArg();
            args3.setName("httplog_url");
            args3.setDefaultValue("http://test.dnslog.com");
            args3.setDescription("httplog地址");
            args3.setRequired(true);
            args.add(args3);

            IArg args4 = pluginHelper.createArg();
            args4.setName("sleep");
            args4.setDefaultValue("10");
            args4.setDescription("sleep时间（秒）");
            args4.setRequired(true);
            args.add(args4);

            argsUsageBinder.setArgsList(args);
            return argsUsageBinder;
        }

        @Override
        public void doHelp(Map<String, Object> map, IResultOutput output) throws Throwable {
            WPSettings settings = new WPSettings(map);

            if (settings.getString("command") != null) {
                String[] exec = _expr.genExec(settings.getString("command"));
                String[] execWithEcho = _expr.genExecWithEcho(settings.getString("command"));
                output.successPrintln("命令执行：");
                printResult(exec, output);
                output.successPrintln("命令执行(回显输出)：");
                printResult(execWithEcho, output);
            }

            if (settings.getString("dnslog_domain") != null) {
                String[] results = _expr.genDnslog(settings.getString("dnslog_domain"));
                output.successPrintln("DnsLog：");
                printResult(results, output);
            }

            if (settings.getString("httplog_url") != null) {
                String[] results = _expr.genHttplog(settings.getString("httplog_url"));
                output.successPrintln("HttpLog：");
                printResult(results, output);
            }

            if (settings.getString("sleep") != null) {
                String[] results = _expr.genSleep(settings.getInteger("sleep", 1));
                output.successPrintln("Sleep：");
                printResult(results, output);
            }
        }

        private void printResult(String[] results, IResultOutput output) {
            if (results != null && results.length > 0) {
                output.rawPrintln("");
                for (String result :
                        results) {
                    output.rawPrintln(result);
                    output.rawPrintln("");
                }
            } else {
                output.warningPrintln("暂不支持");
                output.rawPrintln("");
            }
        }
    }
}
