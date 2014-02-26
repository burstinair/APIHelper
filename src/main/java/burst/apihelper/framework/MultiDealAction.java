package burst.apihelper.framework;

import java.util.List;

/**
 * @author zhongkai.zhao
 *         14-1-10 下午3:28
 */
public abstract class MultiDealAction implements IAction {

    protected abstract Object deal(String param) throws Throwable;

    protected List<String> defaultParams(Request request, Context context) {
        return null;
    }

    protected String formatMessage(String param) {
        return "Dealing with parameter " + param;
    }

    protected String formatErrorMessage(String param, Throwable ex) {
        return "Error with parameter " + param;
    }

    @Override
    public void execute(Request request, Context context) throws Throwable {
        List<String> params = request.getOptions(request.getPath());
        if(params == null || params.size() == 0) {
            params = defaultParams(request, context);
        }
        if(params != null && params.size() > 0) {
            for(String param : params) {
                try {
                    if(request.isInit() && !request.isSilence()) {
                        context.getPrinter().printWithNewLine(formatMessage(param));
                    }
                    context.getPrinter().printWithNewLine(deal(param).toString());
                } catch (Throwable ex) {
                    context.getPrinter().printWithNewLine(formatErrorMessage(param, ex));
                }
            }
            if(!request.isSilence()) {
                context.setPauseToShowResult(true);
            }
        }
    }
}
