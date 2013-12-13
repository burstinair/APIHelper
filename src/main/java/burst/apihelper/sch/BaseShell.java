package burst.apihelper.sch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * @author zhongkai.zhao
 *         13-12-13 下午8:44
 */
public abstract class BaseShell implements IShell {

    protected static final String CHANNEL_SHELL = "shell";

    protected void init(SchRequest request) throws JSchException { }

    private Channel channel;

    protected void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return channel.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return channel.getOutputStream();
    }

    public String exec(String command) throws IOException {
        getOutputStream().write(command.getBytes());
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer = new byte[2048];
        while(getInputStream().read(buffer) > 0) {
            stringBuilder.append(Arrays.toString(buffer));
        }
        return stringBuilder.toString();
    }
}
