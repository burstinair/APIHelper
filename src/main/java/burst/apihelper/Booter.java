package burst.apihelper;

import java.util.NoSuchElementException;
import java.util.Scanner;

import burst.apihelper.framework.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * User: zhongkai.zhao
 * Date: 13-8-1
 * Time: 下午4:59
 */
@Component
public class Booter implements ApplicationContextAware {

    @Autowired
    private RequestBuilder requestBuilder;

    private void keep(Context context) throws InterruptedException {
        while(context.isKeepAlive()) {
            Thread.sleep(1000);
        }
    }

    @PostConstruct
    public void boot() throws Throwable {
        Request request = requestBuilder.build(INIT_ARGS, true);
        IAction action = findBean(request.getPath());
        if(action != null) {
            Context context = buildContext();
            action.execute(request, context);
            keep(context);
        } else {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("> ");
                String[] args;
                try {
                    args = scanner.nextLine().split(" ");
                } catch (NoSuchElementException ex) {
                    break;
                }
                request = requestBuilder.build(args, false);
                action = findBean(request.getPath());
                if(action != null) {
                    Context context = buildContext();
                    action.execute(request, context);
                    if(context.isRequireExit()) {
                        break;
                    } else {
                        keep(context);
                    }
                } else {
                    System.out.println("Unknown command.");
                }
                System.out.println();
            }
        }
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private IAction findBean(String path) {
        if(path == null) {
            return null;
        }
        try {
            return applicationContext.getBean(path, IAction.class);
        } catch (BeansException ex) {
            return null;
        }
    }

    private Context buildContext() {
        return applicationContext.getBean(Context.class);
    }

    private static String[] INIT_ARGS;
    private static final String APPLICATION_CONTEXT_CONFIG_PATH = "config/spring/appcontext.xml";

    public static void main(String[] args) {
        INIT_ARGS = args;
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_CONFIG_PATH);
    }
}
