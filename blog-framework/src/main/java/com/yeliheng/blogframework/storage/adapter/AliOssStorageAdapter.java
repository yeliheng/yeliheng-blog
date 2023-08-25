package com.yeliheng.blogframework.storage.adapter;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.yeliheng.blogcommon.config.KodoStorageConfig;
import com.yeliheng.blogcommon.config.OssStorageConfig;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogframework.storage.FileSystemAdapter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * 阿里云OSS存储适配器
 * 文件会存储到OSS中指定的bucket
 * 请提前配置好accessKeyId和accessKeySecret信息
 */
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

    @Override
    public String getPublicUrl(String path) {
        String encodedFileName = "";
        try {
            encodedFileName = URLEncoder.encode(path, "utf-8").replace("+", "%20");
            if(StringUtils.isEmpty(encodedFileName)) {
                return "";
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("%s/%s", KodoStorageConfig.getServerDomain(), encodedFileName);
    }
}
