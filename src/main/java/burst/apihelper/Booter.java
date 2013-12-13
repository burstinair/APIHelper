package burst.apihelper;

import java.util.Scanner;

import burst.apihelper.framework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * User: zhongkai.zhao
 * Date: 13-8-1
 * Time: 下午4:59
 */
@Component
public class Booter {

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
        IAction action = APPLICATION_CONTEXT.getBean(request.getPath(), IAction.class);
        if(action != null) {
            Context context = new Context();
            action.execute(request, context);
            keep(context);
        } else {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("> ");
                String[] args = scanner.nextLine().split(" ");
                request = requestBuilder.build(args, false);
                action = APPLICATION_CONTEXT.getBean(request.getPath(), IAction.class);
                if(action != null) {
                    Context context = new Context();
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

    private static String[] INIT_ARGS;
    private static ApplicationContext APPLICATION_CONTEXT;
    private static final String APPLICATION_CONTEXT_CONFIG_PATH = "config/spring/appcontext.xml";

    public static void main(String[] args) {
        INIT_ARGS = args;
        APPLICATION_CONTEXT = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_CONFIG_PATH);
    }
}
