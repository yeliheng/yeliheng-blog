package com.yeliheng.blogframework.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class FileSystemNew {

        private final FileSystemAdapterNew fileSystemAdapterNew;

        public FileSystemNew(FileSystemAdapterNew fileSystemAdapterNew) {
            this.fileSystemAdapterNew = fileSystemAdapterNew;
        }

        public FileSystemAdapterNew getFileSystemAdapterNew() {
            return fileSystemAdapterNew;
        }

        public void write(MultipartFile file, String path) throws IOException {
            fileSystemAdapterNew.write(file, path);
        }

        public void writeStream(InputStream inputStream, String path) throws IOException {
            fileSystemAdapterNew.writeStream(inputStream, path);
        }

        public boolean delete(String path) {
            return fileSystemAdapterNew.delete(path);
        }

        public boolean exist(String path) {
            return fileSystemAdapterNew.exist(path);
        }
}
