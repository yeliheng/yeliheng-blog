package com.yeliheng.blogsystem.controller.article;


import com.yeliheng.blogsystem.common.CommonResponse;
import com.yeliheng.blogsystem.entity.Article;
import com.yeliheng.blogsystem.service.IArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Api(tags = "文章模块")
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @PostMapping("/add")
    public CommonResponse<Object> add(@Validated @RequestBody Article article){
        articleService.addArticle(article);
        return CommonResponse.success();
    }

    @PutMapping("/update")
    public CommonResponse<Object> update(@Validated @RequestBody Article article){
        articleService.updateArticle(article);
        return CommonResponse.success();
    }
}
