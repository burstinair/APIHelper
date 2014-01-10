package burst.apihelper.hash;

import burst.apihelper.framework.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * User: zhongkai.zhao
 * Date: 13-8-1
 * Time: 下午5:00
 */
@Controller("hash")
public class HashAction implements IAction {

    @Autowired
    private HashService hashService;

    @Override
    public void execute(Request request, Context context) throws Throwable {
        String word = request.getOption("hash");
        if(request.isInit() && !request.isSilence()) {
            context.getPrinter().printWithNewLine("Hash of " + word);
        }
        context.getPrinter().printWithNewLine(hashService.hashToHex(word));
        if(!request.isSilence()) {
            context.setPauseToShowResult(true);
        }
    }
}
