package com.yeliheng.blogcommon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.file-system.oss")
public class OssStorageConfig {

    private static String endpoint;

    private static String accessKeyId;

    private static String accessKeySecret;

    private static String serverDomain;

    public static String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        OssStorageConfig.endpoint = endpoint;
    }

    public static String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        OssStorageConfig.accessKeyId = accessKeyId;
    }

    public static String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        OssStorageConfig.accessKeySecret = accessKeySecret;
    }

    public static String getServerDomain() {
        return serverDomain;
    }

    public void setServerDomain(String serverDomain) {
        OssStorageConfig.serverDomain = serverDomain;
    }
}
