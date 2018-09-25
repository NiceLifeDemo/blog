package com.rczx.blog.util;


import com.rczx.blog.config.MyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

public class Common {


    public static String WECHAT_REDIRECT_URL;

    public static String WECHAT_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRCT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";




    /**
     * 判断变量是否为空
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断变量是否为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
            return false;
        } else {
            return true;
        }
    }

}
