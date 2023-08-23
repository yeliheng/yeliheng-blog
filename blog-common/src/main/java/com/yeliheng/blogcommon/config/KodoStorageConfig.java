package com.yeliheng.blogcommon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.file-system.kodo")
/**
 * 七牛云存储配置
 */
public class KodoStorageConfig {

    private static String accessKey; //accessKey

    private static String secretKey; //secretKey

    public static String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        KodoStorageConfig.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        KodoStorageConfig.secretKey = secretKey;
    }
}
