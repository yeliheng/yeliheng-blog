package com.yeliheng.blogframework.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileSystem {

    /**
     * 上传文件到本地
     * @param file 文件
     * @param allowedExt 允许的文件扩展名
     * @return 文件路径
     */
    public String upload(MultipartFile file,String path,String[] allowedExt);

    /**
     * 将文件名重新编码
     *
     * @param originalFileExt 原始的文件扩展名
     * @return 编码后的文件名
     */
    public String encodeFilename(String originalFileExt);


}
