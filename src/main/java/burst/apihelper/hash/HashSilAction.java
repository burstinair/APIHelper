package burst.apihelper.hash;

import burst.apihelper.framework.Context;
import burst.apihelper.framework.IAction;
import burst.apihelper.framework.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * User: zhongkai.zhao
 * Date: 13-8-1
 * Time: 下午5:00
 */
@Controller("hash-sil")
public class HashSilAction implements IAction {

    @Autowired
    private HashService hashService;

    @Override
    public void execute(Request request, Context context) throws Throwable {
        String word = request.getOption("hash-sil");
        context.getPrinter().printWithNewLine(hashService.hashToHex(word));
    }
}
