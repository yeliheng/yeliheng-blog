package com.yeliheng.blogsystem.controller.article;


import com.yeliheng.blogsystem.common.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "文章模块")
@RestController
@RequestMapping("/articles")
public class ArticleController {

    @PostMapping("/add")
    public CommonResponse<Object> add(){
        return CommonResponse.success();
    }
}
