package burst.apihelper.sch;

import com.jcraft.jsch.UserInfo;

/**
 * @author zhongkai.zhao
 *         13-12-12 下午2:50
 */
public class SchRequest implements UserInfo {
    private String host;
    private String username;
    private String password;
    private boolean useProxy;
    private String springBoardHost;
    private String validateKey;
    private int springBoardPort;
    private int port;
    private int timeout;

    public SchRequest() { }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassphrase() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean promptPassword(String s) {
        return false;
    }

    @Override
    public boolean promptPassphrase(String s) {
        return false;
    }

    @Override
    public boolean promptYesNo(String s) {
        return false;
    }

    @Override
    public void showMessage(String s) {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateKey() {
        return validateKey;
    }

    public void setValidateKey(String validateKey) {
        this.validateKey = validateKey;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public boolean isUseProxy() {
        return useProxy;
    }

    public void setUseProxy(boolean useProxy) {
        this.useProxy = useProxy;
    }

    public String getSpringBoardHost() {
        return springBoardHost;
    }

    public void setSpringBoardHost(String springBoardHost) {
        this.springBoardHost = springBoardHost;
    }

    public int getSpringBoardPort() {
        return springBoardPort;
    }

    public void setSpringBoardPort(int springBoardPort) {
        this.springBoardPort = springBoardPort;
    }
}