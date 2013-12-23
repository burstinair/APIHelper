package burst.apihelper.hash;

import org.springframework.stereotype.Service;

/**
 * @author zhongkai.zhao
 *         13-12-23 下午5:09
 */
@Service
public class HashService {

    public int hash(String word) {
        int i = word.hashCode();
        return (0xFFFF & i) ^ (i >>> 16);
    }

    public String hashToHex(String word) {
        return "0x" + Integer.toHexString(hash(word));
    }
}
