package com.yeliheng.blogframework.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileSystemAdapterNew{

    public void write(MultipartFile file, String path) throws IOException;

    public void writeStream(InputStream inputStream, String path) throws IOException;

    public boolean delete(String path);

    public boolean exist(String path);
}
