package com.yeliheng.blogframework.storage.adapter;

import com.yeliheng.blogframework.storage.FileSystemAdapter;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;

public class LocalStorageAdapter implements FileSystemAdapter {

    @Override
    public void write(MultipartFile file, String path) throws IOException {
        if(!Paths.get(path).getParent().toFile().exists()) {
            boolean result = Paths.get(path).getParent().toFile().mkdirs();
            if(!result) {
                throw new FileNotFoundException();
            }
        }
        file.transferTo(Paths.get(path));
    }

    @Override
    public void writeStream(InputStream inputStream, String path) throws IOException {
        File file = new File(path);
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }

        fileOutputStream.close();
        inputStream.close();
    }

    @Override
    public boolean delete(String path) {
        File file = new File(path);
        return file.delete();
    }

    @Override
    public boolean exist(String path) {
        return Paths.get(path).toFile().exists();
    }
}
