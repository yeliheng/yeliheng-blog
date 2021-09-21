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
public class ArticleController {
    @Autowired
    private IArticleService articleService;


    /**
     *
     * 新增文章
     * @param article 文章实体
     * @return 请求结果
     */
    @PostMapping("/articles")
    public CommonResponse<Object> add(@Validated @RequestBody Article article){
        articleService.addArticle(article);
        return CommonResponse.success();
    }

    /**
     *
     * 更新文章
     * @param article 文章实体
     * @return 请求结果
     */
    @PutMapping("/articles")
    public CommonResponse<Object> update(@Validated @RequestBody Article article){
        articleService.updateArticle(article);
        return CommonResponse.success();
    }

    /**
     *
     * 根据Id获取具体文章内容
     * @param articleId 文章Id
     * @return 文章实体
     */
    @GetMapping("/articles/{articleId}")
    public CommonResponse<Article> getArticleById(@PathVariable("articleId") Long articleId){
        return CommonResponse.success(articleService.getArticleById(articleId));
    }

    @GetMapping("/articles")
    public CommonResponse<Object> getArticles(@RequestParam("page") Integer page,@RequestParam("pageSize") Integer pageSize){

        return CommonResponse.success(articleService.getArticles(page,pageSize));
    }

}
