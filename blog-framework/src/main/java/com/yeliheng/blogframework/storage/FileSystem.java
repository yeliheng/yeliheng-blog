package com.yeliheng.blogframework.storage;

import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.utils.DateUtils;
import com.yeliheng.blogcommon.utils.UUIDUtils;
import com.yeliheng.blogframework.config.LocalStorageConfig;
import com.yeliheng.blogframework.config.OssStorageConfig;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class FileSystem {

    /**
     * 处理文件
     *
     * @param file       文件
     * @param path       文件路径
     * @param allowedExt 允许的文件扩展名
     * @return 文件路径
     */
    public String handleFile(MultipartFile file, String path, String[] allowedExt) {
        return null;
    }


    /**
     * 处理文件
     *
     * @param file 文件
     * @param bucket bucket
     * @param object bucket object
     * @param allowedExt 允许的文件扩展名
     * @return 文件路径
     */
    public String handleFile(MultipartFile file, String bucket, String object, String[] allowedExt) {
        return null;
    }

    /**
     * 将文件名重新编码
     *
     * @param originalFileExt 原始的文件扩展名
     * @return 编码后的文件名
     */
    public String encodeFilename(String originalFileExt) {
        return DateUtils.getUnsignedLocalDateTimeStr() +  "-" + UUIDUtils.generateSimpleUUID() + "." + originalFileExt;
    }

    public void checkFileAllowed(MultipartFile file,String[] allowedExt) {
        // 检查文件名长度
        if(file.getOriginalFilename().length() > LocalStorageConfig.getMaxFilenameLength())
            throw new RequestFormatException("文件名过长!");
        //检查文件大小
        if(file.getSize() > LocalStorageConfig.getMaxFileSize()  * 1024 * 1024)
            throw new RequestFormatException("文件大小超过限制!");
        String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
        boolean allowed = false;
        //检查文件类型是否允许
        for (String ext : allowedExt) {
            if(ext.equalsIgnoreCase(fileExt)) {
                allowed = true;
            }
        }
        if(!allowed) throw new RequestFormatException("文件类型不合法!");

    }


}
