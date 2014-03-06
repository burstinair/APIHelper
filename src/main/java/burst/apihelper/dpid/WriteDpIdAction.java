package burst.apihelper.dpid;

import burst.apihelper.framework.Context;
import burst.apihelper.framework.IAction;
import burst.apihelper.framework.Request;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhongkai.zhao
 *         14-3-6 下午6:41
 */
@Controller("wd")
public class WriteDpIdAction implements IAction {

    private String nextDpId() {
        return String.valueOf((long) (Math.random() * 20000000000L) - 10000000000L) + ((long) (Math.random() * 1000000000L));
    }

    @Override
    public void execute(Request request, Context context) throws Throwable {
        List<String> options = request.getOptions("wd");
        try {
            String fileName = options.get(0);
            int length = Integer.parseInt(options.get(1));
            List<String> result = Lists.newArrayList();
            for(int i = 0; i < length; ++i) {
                result.add(nextDpId());
            }
            FileUtils.writeLines(new File(fileName), result);
            context.getPrinter().printWithNewLine("done");
        } catch (NumberFormatException e) {
            context.getPrinter().printWithNewLine("number format error");
        } catch (IOException e) {
            context.getPrinter().printWithNewLine("write file error");
        }
    }
}
