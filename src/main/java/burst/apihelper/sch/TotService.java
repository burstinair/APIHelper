package burst.apihelper.sch;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhongkai.zhao
 *         14-2-26 上午10:29
 */
@Service
public class TotService {

    private static final String HMAC_SHA1 = "HmacSHA1";
    private static final int TIME_INTERVAL = 30000;
    private static final int PASS_CODE_CUTTER = 1000000;
    private static final String PASS_CODE_FORMAT = "%06d";

    public String calcCodeAsString(String rawSecret) {
        return String.format(PASS_CODE_FORMAT, calcCode(rawSecret));
    }

    private Base32 BASE32_DECODER = new Base32();
    private Map<String, SecretKeySpec> SECRET_MAP = new ConcurrentHashMap<String, SecretKeySpec>();
    private SecretKeySpec getSecret(String rawSecret) {
        SecretKeySpec res = SECRET_MAP.get(rawSecret);
        if(res == null) {
            res = new SecretKeySpec(BASE32_DECODER.decode(rawSecret), HMAC_SHA1);
            SECRET_MAP.put(rawSecret, res);
        }
        return res;
    }

    public int calcCode(String rawSecret) {

        try {

            byte[] time = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN)
                    .putLong(System.currentTimeMillis() / TIME_INTERVAL).array();

            Mac mac = Mac.getInstance(HMAC_SHA1);
            mac.init(getSecret(rawSecret));
            byte[] hash = mac.doFinal(time);

            int offset = hash[hash.length - 1] & 0xf;
            return (ByteBuffer.wrap(ArrayUtils.subarray(hash, offset, offset + 4))
                .order(ByteOrder.BIG_ENDIAN).getInt() & 0x7FFFFFFF) % PASS_CODE_CUTTER;

        } catch (Throwable ex) {
            throw new RuntimeException("calc Code failed", ex);
        }

    }
}
