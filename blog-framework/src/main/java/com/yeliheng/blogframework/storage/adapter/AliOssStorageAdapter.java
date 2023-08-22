package com.yeliheng.blogframework.storage.adapter;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yeliheng.blogcommon.config.OssStorageConfig;
import com.yeliheng.blogframework.storage.FileSystemAdapter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class AliOssStorageAdapter implements FileSystemAdapter {

    private final String endpoint = OssStorageConfig.getEndpoint();
    private final String accessKeyId = OssStorageConfig.getAccessKeyId();
    private final String accessKeySecret = OssStorageConfig.getAccessKeySecret();
    private final String bucket;

    public AliOssStorageAdapter(String bucket) {
        this.bucket = bucket;
    }

    @Override
    public void write(MultipartFile file, String path) throws IOException {
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.putObject(bucket, path, file.getInputStream());
        ossClient.shutdown();
    }

    @Override
    public void writeStream(InputStream inputStream, String path) {
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.putObject(bucket, path, inputStream);
        ossClient.shutdown();
    }

    @Override
    public boolean delete(String path) {
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        ossClient.deleteObject(bucket, path);
        ossClient.shutdown();
        return true;
    }

    @Override
    public boolean exist(String path) {
        OSS ossClient = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        boolean exist = ossClient.doesObjectExist(bucket, path);
        ossClient.shutdown();
        return exist;
    }
}
