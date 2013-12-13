package burst.apihelper.framework;

import org.springframework.stereotype.Controller;

/**
 * @author zhongkai.zhao
 *         13-12-12 下午1:21
 */
@Controller("exit")
public class ExitAction implements IAction {

    @Override
    public void execute(Request request, Context context) throws Throwable {
        context.setRequireExit(true);
    }
}
