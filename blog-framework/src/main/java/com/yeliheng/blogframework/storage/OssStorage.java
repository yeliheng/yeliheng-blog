package com.yeliheng.blogframework.storage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogframework.config.LocalStorageConfig;
import com.yeliheng.blogframework.config.OssStorageConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;
import java.util.Date;

public class OssStorage extends FileSystem{

    private final String endpoint = OssStorageConfig.getEndpoint();

    private final String accessKeyId = OssStorageConfig.getAccessKeyId();

    private final String accessKeySecret = OssStorageConfig.getAccessKeySecret();


    /**
     * 处理文件
     * @param multipartFile 表单-文件
     * @param bucket bucket
     * @param object bucket object
     * @param allowedExt 允许的文件扩展名
     * @return 文件路径
     */
    @Override
    public String handleFile(MultipartFile multipartFile, String bucket, String object, String[] allowedExt) {
        //检查文件是否允许
        checkFileAllowed(multipartFile,allowedExt);
        //初始化OSS客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);

        String finalFilename = encodeFilename(FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        String path = object + "/" + finalFilename;


        File file = new File(multipartFile.getOriginalFilename());
        try {
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),file);
            ossClient.putObject(bucket, path, file);
        }catch (Exception e) {
            e.printStackTrace();
            throw new UnexpectedException();
        }

        Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
        URL url = ossClient.generatePresignedUrl(bucket,path,expiration);
        ossClient.shutdown();
        return url.toString();

    }
}
