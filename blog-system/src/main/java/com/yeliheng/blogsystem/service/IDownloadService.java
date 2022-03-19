package com.yeliheng.blogsystem.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IDownloadService {

    /**
     * 下载本地文件
     * @param path 文件路径
     * @param response 响应
     * @param request 请求
     */
    void downloadLocal(String path, HttpServletResponse response, HttpServletRequest request);

}
