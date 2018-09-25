package com.rczx.blog.util;

public class ByteHexUtils {
    public ByteHexUtils() {
    }

    public static String parseByte2HexStr(byte[] bufs) {
        StringBuilder sb = new StringBuilder();
        byte[] var2 = bufs;
        int var3 = bufs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            byte aBuf = var2[var4];
            String hex = Integer.toHexString(aBuf & 255);
            if(hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if(hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }
}
