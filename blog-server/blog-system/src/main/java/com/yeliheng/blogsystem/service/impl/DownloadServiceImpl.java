package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogcommon.config.LocalStorageConfig;
import com.yeliheng.blogcommon.exception.NotFoundException;
import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.exception.UnexpectedException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.service.IDownloadService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class DownloadServiceImpl implements IDownloadService {


    /**
     * 下载本地文件
     *
     * @param path     文件路径
     * @param response 响应
     * @param request  请求
     */
    @Override
    public void downloadLocal(String path, HttpServletResponse response, HttpServletRequest request) {
        if(!checkAllowDownload(path)) {
            throw new RequestFormatException("文件路径不合法！");
        }
        String absolutePath = LocalStorageConfig.getFilePath() + path;
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(absolutePath)); OutputStream outputStream = response.getOutputStream()) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) {
                throw new NotFoundException("文件不存在!");
            }
            e.printStackTrace();
        }


    }

    /**
     * 检查文件是否合法
     *
     * @param path 文件路径
     * @return true/false
     */
    public static boolean checkAllowDownload(String path)
    {
        if(StringUtils.isNull(path)) return false;

        if (StringUtils.contains(path, "..")) return false;

        return true;
    }
}
