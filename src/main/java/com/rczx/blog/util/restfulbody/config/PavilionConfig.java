
package com.rczx.blog.util.restfulbody.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
    prefix = "pavilion.config"
)
public class PavilionConfig {
    private String exceptionHandlerType = "dosser";
    private String amapKey;
    private String uploadType = "oss";
    private String swiftTokenUrl;
    private String swiftUsername;
    private String swiftPassword;
    private String swiftStorageUrl;
    private String swiftAccount;
    private String swiftContainer;
    private String endpoint = "http://oss-cn-qingdao.aliyuncs.com/";
    private String accessKeyId = "LTAIGvdzsWrk98Bf";
    private String accessKeySecret = "IeQT8VzUW56gzZzK172UGzIRcECiT2";
    private String bucketName = "picasso-dev";
    private String fastSqlDbType = "postgresql";

    public PavilionConfig() {
    }

    public String getFastSqlDbType() {
        return this.fastSqlDbType;
    }

    public void setFastSqlDbType(String fastSqlDbType) {
        this.fastSqlDbType = fastSqlDbType;
    }

    public String getSwiftStorageUrl() {
        return this.swiftStorageUrl;
    }

    public void setSwiftStorageUrl(String swiftStorageUrl) {
        this.swiftStorageUrl = swiftStorageUrl;
    }

    public String getExceptionHandlerType() {
        return this.exceptionHandlerType;
    }

    public void setExceptionHandlerType(String exceptionHandlerType) {
        this.exceptionHandlerType = exceptionHandlerType;
    }

    public String getSwiftAccount() {
        return this.swiftAccount;
    }

    public void setSwiftAccount(String swiftAccount) {
        this.swiftAccount = swiftAccount;
    }

    public String getSwiftContainer() {
        return this.swiftContainer;
    }

    public String getUploadType() {
        return this.uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public void setSwiftContainer(String swiftContainer) {
        this.swiftContainer = swiftContainer;
    }

    public String getSwiftTokenUrl() {
        return this.swiftTokenUrl;
    }

    public void setSwiftTokenUrl(String swiftTokenUrl) {
        this.swiftTokenUrl = swiftTokenUrl;
    }

    public String getSwiftUsername() {
        return this.swiftUsername;
    }

    public void setSwiftUsername(String swiftUsername) {
        this.swiftUsername = swiftUsername;
    }

    public String getSwiftPassword() {
        return this.swiftPassword;
    }

    public void setSwiftPassword(String swiftPassword) {
        this.swiftPassword = swiftPassword;
    }

    public String getAmapKey() {
        return this.amapKey;
    }

    public void setAmapKey(String amapKey) {
        this.amapKey = amapKey;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return this.accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return this.accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
