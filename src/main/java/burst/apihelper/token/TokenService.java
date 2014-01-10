package burst.apihelper.token;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhongkai.zhao
 *         14-1-10 上午11:51
 */
@Service
public class TokenService {

    private static final String TRANSFORMATION = "AES/CBC/NoPadding";
    private static final String ALGORITHM = "AES";
    private static final String DEFAULT_ENCODING = "UTF-8";

    private byte[] process(byte[] bytes, int mode) throws Throwable {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        byte[] keyBytes = TOKEN_KEY.getBytes(DEFAULT_ENCODING);
        byte[] ivBytes = TOKEN_IV.getBytes(DEFAULT_ENCODING);
        cipher.init(mode, new SecretKeySpec(keyBytes, ALGORITHM), new IvParameterSpec(ivBytes));
        return cipher.doFinal(bytes);
    }

    private static final String KEY = "D7C6F71A12153EE5";
    private static final String IV = "55C930D827BDABFD";
    private static final String TOKEN_KEY = "55C930D827BDABFD";
    private static final String TOKEN_IV = "D7C6F71A12153EE5";

    private byte[] decrypt(byte[] bytes) throws Throwable {
        byte[] decBuffer = this.process(bytes, Cipher.DECRYPT_MODE);
        int blank = 0;
        for (int i = decBuffer.length - 1; i >= 0; i--) {
            if (decBuffer[i] == '\0') {
                blank++;
            } else {
                break;
            }
        }
        byte[] result = decBuffer;
        if (blank > 0) {
            byte[] decBytes = new byte[decBuffer.length - blank];
            System.arraycopy(decBuffer, 0, decBytes, 0, decBytes.length);
            result = decBytes;
        }
        return result;
    }

    private byte[] encrypt(byte[] bytes) throws Throwable {
        byte[] pBytes;
        if (bytes.length % 16 == 0) {
            pBytes = bytes;
        } else {
            pBytes = new byte[bytes.length + (16 - bytes.length % 16)];
            System.arraycopy(bytes, 0, pBytes, 0, bytes.length);
        }

        return this.process(pBytes, Cipher.ENCRYPT_MODE);
    }

    private static String toHexString(byte[] byteArray) {
        StringBuilder md5StrBuff = new StringBuilder();
        for (byte aByteArray : byteArray) {
            String h = Integer.toHexString(0xFF & aByteArray);
            while (h.length() < 2) {
                h = "0" + h;
            }
            md5StrBuff.append(h);
        }
        return md5StrBuff.toString();
    }

    private static byte[] fromHexString(String hexString) {
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        if(c>='0' && c<='9') {
            return (byte)( c-'0');
        }
        else {
            return (byte)( c - 'a' + 10) ;
        }
    }

    private static final String TOKEN_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String TOKEN_ENCODING = "UTF-8";

    public int parseToken(String token) throws Throwable {
        byte[] bytes = fromHexString(token);
        byte[] data = decrypt(bytes);
        String temp = new String(data, TOKEN_ENCODING);
        String[] user = temp.trim().split("\\|");
        return Integer.parseInt(user[0]);
    }

    public String createToken(int userId) throws Throwable {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(TOKEN_TIME_FORMAT);
        String token = Integer.toString(userId) + "|" + formatter.format(now);
        byte[] before = token.getBytes(TOKEN_ENCODING);
        byte[] bytes = encrypt(before);
        return toHexString(bytes);
    }
}
