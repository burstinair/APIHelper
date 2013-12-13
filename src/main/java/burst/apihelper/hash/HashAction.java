package burst.apihelper.hash;

import burst.apihelper.framework.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * User: zhongkai.zhao
 * Date: 13-8-1
 * Time: 下午5:00
 */
@Controller("hash")
@Component("hash-sil")
public class HashAction implements IAction {

    @Override
    public void execute(Request request, Context context) throws Throwable {
        String word = request.getOption("hash");
        int i = word.hashCode();
        if(request.getPath().equals("hash") && request.isInit()) {
            context.getPrinter().printWithNewLine("Hash of " + word);
        }
        context.getPrinter().printWithNewLine("0x" + Integer.toHexString((0xFFFF & i) ^ (i >>> 16)));
    }
}
