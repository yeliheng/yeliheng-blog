package com.yeliheng.blogsystem.controller.article;


import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Article;
import com.yeliheng.blogsystem.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "文章模块")
@RestController
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @PostMapping("/articles/add")
    public CommonResponse<Object> add(@Validated @RequestBody Article article){
        articleService.addArticle(article);
        return CommonResponse.success();
    }
}
