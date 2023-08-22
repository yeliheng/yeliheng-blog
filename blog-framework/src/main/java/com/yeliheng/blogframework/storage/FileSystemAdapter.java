package com.yeliheng.blogframework.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileSystemAdapter {

    /**
     * 将文件写入到指定的路径
     * @param file 文件
     * @param path 路径
     * @throws IOException IO异常
     */
    public void write(MultipartFile file, String path) throws IOException;

    /**
     * 将流写入到指定的路径
     * @param inputStream 输入流
     * @param path 路径
     * @throws IOException IO异常
     */
    public void writeStream(InputStream inputStream, String path) throws IOException;

    /**
     * 删除指定路径的文件
     * @param path 路径
     * @return 是否删除成功
     */
    public boolean delete(String path);

    /**
     * 判断指定路径的文件是否存在
     * @param path 路径
     * @return 是否存在
     */
    public boolean exist(String path);
}
