package com.rczx.blog.util.restfulbody.encrypt;


import com.rczx.blog.util.ByteHexUtils;

import java.nio.charset.Charset;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class AESUtils {
    public static String AES_KEY = "87164a3a1949438d";
    public static String AES_MODE = "AES/ECB/PKCS5Padding";

    public AESUtils() {
    }

    public static byte[] encrypt(String content) {
        try {
            SecretKeySpec e = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(AES_MODE);
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(1, e);
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] content) {
        try {
            SecretKeySpec e = new SecretKeySpec(AES_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance(AES_MODE);
            cipher.init(2, e);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public static String encryptHex(String content) {
        return ByteHexUtils.parseByte2HexStr(encrypt(content));
    }

    public static String decryptFromHex(String content) {
        return content == null?null:new String(decrypt(ByteHexUtils.parseHexStr2Byte(content)), Charset.forName("utf-8"));
    }

    public static String encryptHex(String content, String pepperContent) {
        return ByteHexUtils.parseByte2HexStr(encrypt(pepperContent + content));
    }

    public static String decryptFromHex(String content, int pepperLength) {
        return content == null?null:(new String(decrypt(ByteHexUtils.parseHexStr2Byte(content)), Charset.forName("utf-8"))).substring(pepperLength);
    }
}
