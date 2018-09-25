package com.rczx.blog.config;


import org.springframework.boot.context.properties.ConfigurationProperties;

   @ConfigurationProperties(prefix = "config")
    public class MyConfig {


    private String wechatUrl;

    public String getWechatUrl() {
        return wechatUrl;
    }

    public void setWechatUrl(String wechatUrl) {
        this.wechatUrl = wechatUrl;
    }


}
