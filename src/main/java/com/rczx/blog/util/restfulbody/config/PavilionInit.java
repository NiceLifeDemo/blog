
package com.rczx.blog.util.restfulbody.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class PavilionInit implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(PavilionInit.class);
    @Autowired
    private PavilionConfig pavilionConfig;

    public PavilionInit() {
    }

    public void run(String... strings) throws Exception {
      /*  logger.info("pavilion-------->PavilionInit Start--------");
        AMApUtils.KEY = this.pavilionConfig.getAmapKey();
        Swift.pavilionConfig = this.pavilionConfig;
        UploadUtils.pavilionConfig = this.pavilionConfig;
        PageSqlUtils.DB_TYPE = this.pavilionConfig.getFastSqlDbType();
        logger.info("pavilion-------->PavilionInit End-----------");*/
    }
}
