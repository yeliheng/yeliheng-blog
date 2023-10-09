package com.yeliheng.blogframework.storage;

import com.yeliheng.blogcommon.utils.DateUtils;
import com.yeliheng.blogcommon.utils.UUIDUtils;

public class FileUtils {

    /**
     * 获取文件名
     * @param fileName 文件名
     * @return 文件名
     */
    public static String getFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 将文件名重新编码
     * @param originalFileExt 原始的文件扩展名
     * @return 编码后的文件名
     */
    public static String encodeFileNameWithUUID(String originalFileExt) {
        return DateUtils.getUnsignedLocalDateTimeStr() +  "-" + UUIDUtils.generateSimpleUUID() + "." + originalFileExt;
    }



}
