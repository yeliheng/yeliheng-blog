package com.yeliheng.blogframework.storage;

import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.utils.DateUtils;
import com.yeliheng.blogcommon.utils.UUIDUtils;
import com.yeliheng.blogframework.config.FrameworkConfig;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


public class Storage implements FileSystem{

    private static final Logger logger = LoggerFactory.getLogger(Storage.class);

    private final String disk; //磁盘

    public Storage(String disk) {
        this.disk = disk;
    }


    /**
     * 上传文件到本地
     *
     * @param file       文件
     * @param allowedExt 允许的文件扩展名
     * @return 文件路径
     */
    @Override
    public String upload(MultipartFile file, String path,String[] allowedExt) {
        // 检查文件名长度
        if(file.getOriginalFilename().length() > FrameworkConfig.getMaxFilenameLength())
            throw new RequestFormatException("文件名过长!");
        //检查文件大小
        if(file.getSize() > FrameworkConfig.getMaxFileSize()  * 1024 * 1024)
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
        String finalFilename = encodeFilename(fileExt);
        File finalFile = new File(FrameworkConfig.getFilePath() + "/" + disk + "/" + path + "/" + finalFilename);
        if(!finalFile.exists()) {
            if(!finalFile.getParentFile().exists()) {
                finalFile.getParentFile().mkdirs();
            }
        }
        try {
            file.transferTo(finalFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException();
        }
        return null;
    }

    /**
     * 将文件名重新编码
     *
     * @param originalFileExt 原始的文件扩展名
     * @return 编码后的文件名
     */
    @Override
    public String encodeFilename(String originalFileExt) {
        return DateUtils.getUnsignedLocalDateTimeStr() +  "-" + UUIDUtils.generateSimpleUUID() + "." + originalFileExt;
    }


}
