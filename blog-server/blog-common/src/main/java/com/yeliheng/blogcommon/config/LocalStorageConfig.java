package com.yeliheng.blogcommon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.file-system.local")
public class LocalStorageConfig {

    private static String filePath;

    private static String serverDomain;

    public static String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        LocalStorageConfig.filePath = filePath;
    }

    public static String getServerDomain() {
        return serverDomain;
    }

    public void setServerDomain(String serverDomain) {
        LocalStorageConfig.serverDomain = serverDomain;
    }
}
