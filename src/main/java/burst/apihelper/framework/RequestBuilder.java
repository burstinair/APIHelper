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
        boolean silence = false;

        String key = null;
        List<String> curValues = new ArrayList<String>();
        for(int i = 0; i < args.length; ++i) {
            if(i == 0) {
                key = StringUtils.trimLeadingCharacter(args[i], '-');
                path = key;
            } else if(args[i].startsWith("-")) {
                if("-sil".equalsIgnoreCase(args[i]) || "-silence".equalsIgnoreCase(args[i])) {
                    silence = true;
                } else {
                    options.put(key, curValues);
                    curValues = new ArrayList<String>();
                    key = StringUtils.trimLeadingCharacter(args[i], '-');
                }
            } else {
                curValues.add(args[i]);
            }
        }
        options.put(key, curValues);
        return new Request(options, path, init, silence);
    }
}
