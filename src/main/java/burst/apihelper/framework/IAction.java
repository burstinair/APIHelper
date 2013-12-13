package burst.apihelper.framework;

/**
 * @author zhongkai.zhao
 *         13-12-12 下午12:34
 */
public interface IAction {

    void execute(Request request, Context context) throws  Throwable;
}
