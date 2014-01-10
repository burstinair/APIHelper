package burst.apihelper.token;

import burst.apihelper.framework.MultiDealAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author zhongkai.zhao
 *         14-1-10 上午11:50
 */
@Controller("ct")
public class CreateTokenAction extends MultiDealAction {

    @Autowired
    private TokenService tokenService;

    @Override
    protected String formatMessage(String param) {
        return "Create token of " + param;
    }

    @Override
    protected Object deal(String param) throws Throwable {
        return tokenService.createToken(Integer.parseInt(param));
    }
}
