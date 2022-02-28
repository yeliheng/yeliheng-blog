package com.yeliheng.blogframework.storage;

import org.springframework.web.multipart.MultipartFile;

public class FileSystemAdapter{

    private final FileSystem fileSystem;

    public FileSystemAdapter(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }
}
