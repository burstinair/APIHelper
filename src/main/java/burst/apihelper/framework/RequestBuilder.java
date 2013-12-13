package burst.apihelper.framework;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhongkai.zhao
 *         13-12-12 下午12:16
 */
@Component
public class RequestBuilder {

    public Request build(String[] args, boolean init) {
        Map<String, List<String>> options = new HashMap<String, List<String>>();
        String path = null;

        String key = null;
        List<String> curValues = new ArrayList<String>();
        for(int i = 0; i < args.length; ++i) {
            if(i == 0) {
                path = args[i];
                key = StringUtils.trimLeadingCharacter(args[i], '-');
            } else if(args[i].startsWith("-")) {
                options.put(key, curValues);
                key = StringUtils.trimLeadingCharacter(args[i], '-');
            } else {
                curValues.add(args[i]);
            }
        }
        options.put(key, curValues);
        return new Request(options, path, init);
    }
}
