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
@Controller("pt")
public class ParseTokenAction implements IAction {

    @Autowired
    private TokenService tokenService;

    @Override
    public void execute(Request request, Context context) throws Throwable {
        for(String token : request.getOptions("pt")) {
            try {
                if(request.isInit()) {
                    context.getPrinter().printWithNewLine("Parse token of " + token);
                }
                context.getPrinter().printWithNewLine(Integer.toString(tokenService.parseToken(token)));
            } catch (Throwable ex) {
                context.getPrinter().printWithNewLine("inValid token");
            }
        }
        if(!request.isSilence()) {
            context.setPauseToShowResult(true);
        }
    }
}
