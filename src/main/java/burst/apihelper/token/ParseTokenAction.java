package burst.apihelper.token;

import burst.apihelper.framework.MultiDealAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhongkai.zhao
 *         14-1-10 上午11:50
 */
@Controller("pt")
public class ParseTokenAction extends MultiDealAction {

    @Autowired
    private TokenService tokenService;

    @Override
    protected String formatMessage(String param, boolean isDefault) {
        return "Parse token " + param;
    }

    @Override
    protected Object deal(String param) throws Throwable {
        return tokenService.parseToken(param);
    }
}
