package com.yeliheng.blogweb.controller;

import com.yeliheng.blogsystem.service.IDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/admin/download")
public class DownloadController {

    @Autowired
    private IDownloadService downloadService;

    @GetMapping()
    public void download(String path, HttpServletResponse response, HttpServletRequest request) {
        downloadService.downloadLocal(path, response, request);
    }

}
