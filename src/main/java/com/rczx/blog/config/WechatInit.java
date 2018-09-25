package com.rczx.blog.config;

import com.rczx.blog.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WechatInit implements CommandLineRunner {
    @Autowired
    private MyConfig myConfig;

    @Override
    public void run(String... args) throws Exception {

        log.info("初始化微信配置");

        Common.WECHAT_REDIRECT_URL=myConfig.getWechatUrl();

        log.info("初始化微信配置完成");

    }
}
