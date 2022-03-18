package com.yeliheng.blogframework.storage;

import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.config.LocalStorageConfig;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class LocalStorage extends FileSystem{

    private final String disk; //磁盘

    public LocalStorage(String disk) {
        this.disk = disk;
    }

    /**
     * 处理文件
     *
     * @param file       文件
     * @param allowedExt 允许的文件扩展名
     * @return 文件路径
     */
    @Override
    public String handleFile(MultipartFile file, String path,String[] allowedExt) {
        //检查文件是否允许
        checkFileAllowed(file,allowedExt);
        String finalFilename = encodeFilename(FilenameUtils.getExtension(file.getOriginalFilename()));
        File finalFile = new File(LocalStorageConfig.getFilePath() + "/" + disk + "/" + path + "/" + finalFilename);
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
        return "/" + disk + "/" + path + "/" + finalFilename;
    }

}
