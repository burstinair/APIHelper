package burst.apihelper.framework;

/**
 * @author zhongkai.zhao
 *         14-1-10 下午3:28
 */
public abstract class MultiDealAction implements IAction {

    protected abstract Object deal(String param) throws Throwable;

    protected String formatMessage(String param) {
        return "Dealing with parameter " + param;
    }

    protected String formatErrorMessage(String param, Throwable ex) {
        return "unValid parameter " + param;
    }

    @Override
    public void execute(Request request, Context context) throws Throwable {
        for(String param : request.getOptions(request.getPath())) {
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
