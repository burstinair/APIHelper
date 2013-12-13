package burst.apihelper.framework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zhongkai.zhao
 *         13-12-12 下午12:38
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Context {

    private static final String SETTINGS_LOCATION = "settings.properties";

    private Properties properties;

    @Autowired
    private IPrinter printer;

    private boolean requireExit;

    private boolean keepAlive;

    public Context() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(SETTINGS_LOCATION);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            Logger logger = Logger.getLogger("context");
            logger.log(Level.WARNING, "load settings failed");
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public IPrinter getPrinter() {
        return printer;
    }

    public boolean isRequireExit() {
        return requireExit;
    }

    public void setRequireExit(boolean requireExit) {
        this.requireExit = requireExit;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }
}
