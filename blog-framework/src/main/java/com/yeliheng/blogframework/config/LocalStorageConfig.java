package com.yeliheng.blogframework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.file-system.local")
public class LocalStorageConfig {

    private static String filePath; //文件系统的绝对路径

    private static int maxFilenameLength; //最大文件名长度

    private static long maxFileSize; //最大文件大小

    public static String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        LocalStorageConfig.filePath = filePath;
    }

    public static int getMaxFilenameLength() {
        return maxFilenameLength;
    }

    public void setMaxFilenameLength(int maxFilenameLength) {
        LocalStorageConfig.maxFilenameLength = maxFilenameLength;
    }

    public static long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        LocalStorageConfig.maxFileSize = maxFileSize;
    }
}
