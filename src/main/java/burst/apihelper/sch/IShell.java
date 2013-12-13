package burst.apihelper.sch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zhongkai.zhao
 *         13-12-13 下午8:38
 */
public interface IShell {

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;
}
