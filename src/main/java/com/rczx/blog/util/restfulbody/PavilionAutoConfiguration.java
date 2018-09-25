
package com.rczx.blog.util.restfulbody;



import com.zm.system.utils.restfulbody.config.PavilionInit;
import com.zm.system.utils.restfulbody.message.dosser.RestExceptionController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zm.system.utils.restfulbody.config.MyMvcConfigurerAdapter;
import com.zm.system.utils.restfulbody.config.PavilionConfig;

@Configuration
@EnableConfigurationProperties({PavilionConfig.class})
public class PavilionAutoConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(PavilionAutoConfiguration.class);

    public PavilionAutoConfiguration() {
    }

    @Bean
    public MyMvcConfigurerAdapter myMvcConfigurerAdapter() {
        logger.info("pavilion-------> MyMvcConfigurerAdapter");
        return new MyMvcConfigurerAdapter();
    }

    @Bean
    public RestExceptionController restExceptionController() {
        logger.info("pavilion------->use restExceptionController");
        return new RestExceptionController();
    }

    @Bean
    public PavilionInit pavilionInit() {
        return new PavilionInit();
    }
}
