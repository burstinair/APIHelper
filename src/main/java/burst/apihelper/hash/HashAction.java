package burst.apihelper.hash;

import burst.apihelper.framework.MultiDealAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * User: zhongkai.zhao
 * Date: 13-8-1
 * Time: 下午5:00
 */
@Controller("hash")
public class HashAction extends MultiDealAction {

    @Autowired
    private HashService hashService;

    @Override
    protected String formatMessage(String param) {
        return "Hash of " + param;
    }

    @Override
    protected Object deal(String param) throws Throwable {
        return hashService.hashToHex(param);
    }
}
