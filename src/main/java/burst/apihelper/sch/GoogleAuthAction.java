package burst.apihelper.sch;

import burst.apihelper.framework.Context;
import burst.apihelper.framework.MultiDealAction;
import burst.apihelper.framework.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhongkai.zhao
 *         14-2-26 上午11:01
 */
@Controller("ga")
public class GoogleAuthAction extends MultiDealAction {

    @Autowired
    private TotService totService;

    @Override
    protected String formatMessage(String param, boolean isDefault) {
        if(isDefault) {
            return "Calculating pass code for default secret...";
        } else {
            return "Calculating pass code for secret " + param;
        }
    }

    @Override
    protected Object deal(String param) throws Throwable {
        return totService.calcCodeAsString(param);
    }

    @Override
    protected List<String> defaultParams(Request request, Context context) {
        return Arrays.asList(context.getProperty("vk"));
    }
}
