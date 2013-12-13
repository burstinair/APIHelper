package burst.apihelper.sch;

import burst.apihelper.framework.Context;
import burst.apihelper.framework.IAction;
import burst.apihelper.framework.Request;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zhongkai.zhao
 *         13-12-12 上午11:56
 */
@Controller("vl")
public class ViewProductLogAction implements IAction {

    private static final int DEFAULT_PORT = 22;
    private static final int DEFAULT_TIMEOUT = 30000;
    private static final String DEFAULT_PROXY = "192.168.8.171";

    private static final Pattern PROJECT_NAME_PATTERN = Pattern.compile("^[\\w\\d]+$");
    private static final Pattern PROJECT_NAME_WITHOUT_API_PATTERN = Pattern.compile("^[\\w\\d]+-$");

    private String getParameter(Request request, Context context, String... keys) {
        String result = null;
        int i = 0;
        while(result == null && i < keys.length) {
            result = request.getOption(keys[i]);
            if(result == null) {
                result = context.getProperty(keys[i]);
            }
        }
        return result;
    }

    private SchRequest buildRequest(Request request, Context context) {
        SchRequest schRequest = new SchRequest();

        String host = null;
        String hostSuffix = "";

        List<String> hostParameter = request.getOptions("vl");
        if(hostParameter != null) {
            if(hostParameter.size() >= 1) {
                host = hostParameter.get(0);
            }
            if(hostParameter.size() > 1) {
                hostSuffix = hostParameter.get(1);
            }
        } else {
            hostParameter = request.getOptions("host");
            if(hostParameter != null) {
                if(hostParameter.size() >= 1) {
                    host = hostParameter.get(0);
                }
                if(hostParameter.size() > 1) {
                    hostSuffix = hostParameter.get(1);
                }
            } else {
                host = context.getProperty("host");
            }
        }
        if(PROJECT_NAME_PATTERN.matcher(host).matches()) {
            host = "mobile-" + host + "api-web";
        } else if(PROJECT_NAME_WITHOUT_API_PATTERN.matcher(host).matches()) {
            host = "mobile-" + host + "web";
        }
        if(hostSuffix == null) {
            hostSuffix = "01.nh";
        }
        host += hostSuffix;

        schRequest.setUsername(getParameter(request, context, "username", "user"));
        schRequest.setPassword(getParameter(request, context, "password", "pwd"));
        schRequest.setHost(host);
        try {
            schRequest.setPort(Integer.parseInt(getParameter(request, context, "port")));
        } catch (NumberFormatException ex) {
            schRequest.setPort(DEFAULT_PORT);
        }
        try {
            schRequest.setTimeout(Integer.parseInt(getParameter(request, context, "timeout")));
        } catch (NumberFormatException ex) {
            schRequest.setTimeout(DEFAULT_TIMEOUT);
        }

        schRequest.setValidateKey(getParameter(request, context, "validateKey", "vk"));
        if(getParameter(request, context, "noproxy") != null) {
            schRequest.setUseProxy(true);
            String proxyHost = getParameter(request, context, "proxyHost", "proxy");
            if(proxyHost != null) {
                schRequest.setSpringBoardHost(proxyHost);
            } else {
                schRequest.setSpringBoardHost(DEFAULT_PROXY);
            }
            try {
                schRequest.setSpringBoardPort(Integer.parseInt(getParameter(request, context, "proxyPort")));
            } catch (NumberFormatException ex) {
                schRequest.setSpringBoardPort(DEFAULT_PORT);
            }
        } else {
            schRequest.setUseProxy(false);
        }

        return schRequest;
    }

    @Override
    public void execute(Request request, Context context) throws Throwable {
        SchRequest schRequest = buildRequest(request, context);
        if(schRequest != null) {
            context.getPrinter().printWithNewLine("start connecting to " + schRequest.getHost() + "...");

            BaseShell shell;
            if(schRequest.isUseProxy()) {
                shell = new SpringBoardShell();
            } else {
                shell = new Shell();
            }
            shell.init(schRequest);

            context.setKeepAlive(true);
        } else {
            context.getPrinter().printWithNewLine("build request failed");
        }
    }
}
