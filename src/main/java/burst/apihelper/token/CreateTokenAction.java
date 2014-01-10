package burst.apihelper.token;

import burst.apihelper.framework.Context;
import burst.apihelper.framework.IAction;
import burst.apihelper.framework.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhongkai.zhao
 *         14-1-10 上午11:50
 */
@Controller("ct")
public class CreateTokenAction implements IAction {

    @Autowired
    private TokenService tokenService;

    @Override
    public void execute(Request request, Context context) throws Throwable {
        try {
            String token = tokenService.createToken(Integer.parseInt(request.getOption("ct")));
            context.getPrinter().printWithNewLine(token);
        } catch (Throwable ex) {
            context.getPrinter().printWithNewLine("unValid userId");
        }
    }
}
