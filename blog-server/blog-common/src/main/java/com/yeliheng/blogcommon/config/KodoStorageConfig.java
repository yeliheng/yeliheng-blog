package com.yeliheng.blogcommon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.file-system.kodo")
public class KodoStorageConfig {

    private static String accessKey;

    private static String secretKey;

    private static String serverDomain;

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

    public static String getServerDomain() {
        return serverDomain;
    }

    public void setServerDomain(String serverDomain) {
        KodoStorageConfig.serverDomain = serverDomain;
    }
}
