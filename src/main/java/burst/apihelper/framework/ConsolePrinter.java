package burst.apihelper.framework;

import org.springframework.stereotype.Component;

/**
 * @author zhongkai.zhao
 *         13-12-12 下午1:36
 */
@Component
public class ConsolePrinter implements IPrinter {

    @Override
    public void print(String data) {
        System.out.print(data);
    }

    @Override
    public void printWithNewLine(String data) {
        System.out.println(data);
    }
}
