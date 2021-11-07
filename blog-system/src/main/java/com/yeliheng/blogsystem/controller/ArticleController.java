package com.yeliheng.blogsystem.controller;


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


    /**
     *
     * 新增文章
     * @param article 文章实体
     * @return 请求结果
     */
    @PostMapping()
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
    @PutMapping()
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
    @GetMapping("/{articleId}")
    public CommonResponse<Article> getArticleById(@PathVariable("articleId") Long articleId){
        return CommonResponse.success(articleService.getArticleById(articleId));
    }

    /**
     *
     * 获取所有文章
     * @param page 当前页
     * @param pageSize 一页多少
     * @return 文章列表
     */
    @GetMapping()
    public CommonResponse<Object> getArticles(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return CommonResponse.success(articleService.getArticles(page,pageSize));
    }

    /**
     *
     * 根据分类获取文章
     * @param categoryId 分类Id
     * @param page 当前页
     * @param pageSize 一页多少
     * @return
     */
    @GetMapping("/category")
    public CommonResponse<Object> getArticlesByCategoryId(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return CommonResponse.success(articleService.getArticlesByCategory(categoryId,page,pageSize));
    }

}
