package com.yeliheng.blogframework.storage.adapter;

import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.yeliheng.blogcommon.config.KodoStorageConfig;
import com.yeliheng.blogframework.storage.FileSystemAdapter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class KodoStorageAdapter implements FileSystemAdapter {

    private final String bucket;
    private final String accessKey = KodoStorageConfig.getAccessKey();
    private final String secretKey = KodoStorageConfig.getSecretKey();
    private final Configuration cfg = new Configuration(Region.autoRegion());
    private Auth auth = Auth.create(accessKey, secretKey);;

    public KodoStorageAdapter(String bucket) {
        this.bucket = bucket;
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2; // 指定分片上传版本为v2

    }

    @Override
    public void write(MultipartFile file, String path) throws IOException {
        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = auth.uploadToken(bucket);
        uploadManager.put(file.getInputStream(), path, upToken, null, null);
    }

    @Override
    public void writeStream(InputStream inputStream, String path) throws IOException {
        UploadManager uploadManager = new UploadManager(cfg);
        String upToken = auth.uploadToken(bucket);
        uploadManager.put(inputStream, path, upToken, null, null);
    }

    @Override
    public boolean delete(String path) {
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, path);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean exist(String path) {
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            FileInfo stat = bucketManager.stat(bucket, path);
            return stat.status == 0;
        } catch (Exception ignored) {
            return false;
        }
    }
}
