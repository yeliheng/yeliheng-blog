package com.yeliheng.blogframework.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class FileSystem {

    private final FileSystemAdapter fileSystemAdapter;

    public FileSystem(FileSystemAdapter fileSystemAdapter) {
        this.fileSystemAdapter = fileSystemAdapter;
    }

    public FileSystemAdapter getFileSystemAdapterNew() {
        return fileSystemAdapter;
    }

    public void write(MultipartFile file, String path) throws IOException {
        fileSystemAdapter.write(file, path);
    }

    public void writeStream(InputStream inputStream, String path) throws IOException {
        fileSystemAdapter.writeStream(inputStream, path);
    }

    public boolean delete(String path) {
        return fileSystemAdapter.delete(path);
    }

    public boolean exist(String path) {
        return fileSystemAdapter.exist(path);
    }

    public String getURL(String path) {
        return fileSystemAdapter.getURL(path);
    }
}
