package com.yeliheng.blogframework.storage;

public class FileSystemAdapter{

    private final FileSystem fileSystem;

    public FileSystemAdapter(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public FileSystem getFileSystem() {
        return fileSystem;
    }
}
