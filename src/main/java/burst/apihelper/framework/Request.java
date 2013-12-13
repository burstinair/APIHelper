package burst.apihelper.framework;

import java.util.List;
import java.util.Map;

/**
 * @author zhongkai.zhao
 *         13-12-12 下午12:34
 */
public class Request {

    private String path;

    private Map<String, List<String>> options;

    private boolean init;

    public Request() { }
    public Request(Map<String, List<String>> rawMap, String path, boolean init) {
        this.options = rawMap;
        this.path = path;
        this.init = init;
    }

    public Map<String, List<String>> getOptions() {
        return options;
    }

    public String getOption(String key) {
        List<String> entry = options.get(key);
        if(entry == null) {
            return null;
        } else if(entry.size() == 0) {
            return "";
        }
        return entry.get(0);
    }

    public List<String> getOptions(String key) {
        return options.get(key);
    }

    public String getPath() {
        return path;
    }

    public boolean isInit() {
        return init;
    }
}
