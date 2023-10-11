package com.yeliheng.blogframework.storage;

import com.yeliheng.blogcommon.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class FileSystem {

    private final FileSystemAdapter fileSystemAdapter;

    public FileSystem(FileSystemAdapter fileSystemAdapter) {
        this.fileSystemAdapter = fileSystemAdapter;
    }

    public FileSystemAdapter getFileSystemAdapter() {
        return fileSystemAdapter;
    }

    public void write(MultipartFile file, String path) throws IOException {
        if(StringUtils.isEmpty(path)) {
            return;
        }
        fileSystemAdapter.write(file, path);
    }

    public void writeStream(InputStream inputStream, String path) throws IOException {
        if(StringUtils.isEmpty(path)) {
            return;
        }
        fileSystemAdapter.writeStream(inputStream, path);
    }

    public boolean delete(String path) {
        if(StringUtils.isEmpty(path)) {
            return false;
        }
        return fileSystemAdapter.delete(path);
    }

    public boolean deleteDirectory(String path) {
        if(StringUtils.isEmpty(path)) {
            return false;
        }
        return fileSystemAdapter.deleteDirectory(path);
    }

    public boolean exist(String path) {
        if(StringUtils.isEmpty(path)) {
            return false;
        }
        return fileSystemAdapter.exist(path);
    }

    public String getPublicURL(String path) {
        if(StringUtils.isEmpty(path)) {
            return "";
        }
        return fileSystemAdapter.getPublicUrl(path);
    }
}
