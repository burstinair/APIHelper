package burst.apihelper.sch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author zhongkai.zhao
 *         13-12-13 下午8:39
 */
public class Shell extends BaseShell {

    @Override
    public void init(SchRequest request) throws JSchException {
        JSch jSch = new JSch();
        Session session = jSch.getSession(request.getUsername(), request.getHost(), request.getPort());
        session.setPassword(request.getPassword());
        session.setTimeout(request.getTimeout());
        session.setUserInfo(request);
        session.connect();
        setChannel(session.openChannel(CHANNEL_SHELL));
    }
}
