package com.yeliheng.blogweb.controller;



import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogcommon.annotation.Upload;
import com.yeliheng.blogcommon.constant.OperateType;
import com.yeliheng.blogcommon.exception.RequestFormatException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.Article;
import com.yeliheng.blogsystem.service.IArticleService;
import com.yeliheng.blogweb.common.CommonResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "文章模块")
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
    @PreAuthorize("@perm.hasPerm('admin:articles:add')")
    @Log(moduleName = "发布文章",operateType = OperateType.INSERT)
    @PostMapping("/admin/articles")
    public CommonResponse<Object> add(@Validated @RequestBody Article article){
        return CommonResponse.success(articleService.addArticle(article));
    }

    /**
     *
     * 更新文章
     * @param article 文章实体
     * @return 请求结果
     */
    @PreAuthorize("@perm.hasPerm('admin:articles:edit')")
    @Log(moduleName = "更新文章",operateType = OperateType.UPDATE)
    @PutMapping("/admin/articles")
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
/*    @GetMapping("/articles/{articleId}")
    public CommonResponse<Article> getArticleById(@PathVariable("articleId") Long articleId){
        return CommonResponse.success(articleService.getArticleById(articleId));
    }*/

    /**
     *
     * 根据url获取具体文章内容
     * @param url url
     * @return 文章实体
     */
    @GetMapping("/articles/{url}")
    public CommonResponse<Article> getArticleByUrl(@PathVariable("url") String url){
        return CommonResponse.success(articleService.getArticleByUrl(url));
    }

    /**
     *
     * 根据Id获取具体文章内容-后台
     * @param articleId 文章Id
     * @return 文章实体
     */
    @PreAuthorize("@perm.hasPerm('admin:articles:list')")
    @GetMapping("/admin/articles/{articleId}")
    public CommonResponse<Article> getArticleByIdBacked(@PathVariable("articleId") Long articleId){
        return CommonResponse.success(articleService.getArticleByIdBacked(articleId));
    }

    /**
     *
     * 获取所有文章
     * @param page 当前页
     * @param pageSize 一页多少
     * @return 文章列表
     */
    @GetMapping("/articles")
    public CommonResponse<Object> getArticles(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return CommonResponse.success(articleService.getArticles(page,pageSize));
    }

    /**
     *
     * 获取后台所有文章
     * @param page 当前页
     * @param pageSize 一页多少
     * @return 文章列表
     */
    @GetMapping("/admin/articles/list")
    @PreAuthorize("@perm.hasPerm('admin:articles:list')")
    public CommonResponse<Object> getArticlesBacked(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
            Article article){
        return CommonResponse.success(articleService.getArticlesBacked(page,pageSize,article));
    }

    /**
     *
     * 根据分类获取文章
     * @param categoryId 分类Id
     * @param page 当前页
     * @param pageSize 一页多少
     * @return
     */
    @GetMapping("/articles/category")
    public CommonResponse<Object> getArticlesByCategoryId(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return CommonResponse.success(articleService.getArticlesByCategory(categoryId,page,pageSize));
    }

    /**
     *
     * 根据分类获取文章
     * @param tagId 标签Id
     * @param page 当前页
     * @param pageSize 一页多少
     * @return
     */
    @GetMapping("/articles/tag")
    public CommonResponse<Object> getArticlesByTagId(
            @RequestParam("tagId") Long tagId,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        return CommonResponse.success(articleService.getArticlesByTag(tagId,page,pageSize));
    }

    @PreAuthorize("@perm.hasPerm('admin:articles:delete')")
    @Log(moduleName = "删除文章",operateType = OperateType.DELETE)
    @DeleteMapping("/admin/articles")
    public CommonResponse<Object> deleteArticle(@RequestParam("id") Long articleId){
        //TODO: 软删除
        articleService.deleteAritcle(articleId);
        return CommonResponse.success();
    }

    @Log(moduleName = "导出文章Excel",operateType = OperateType.EXPORT)
    @PreAuthorize("@perm.hasPerm('admin:articles:export')")
    @GetMapping(value = "/admin/articles/export")
    public CommonResponse<Object> exportExcel(Article article) {
        return CommonResponse.success(articleService.exportArticle(article));
    }

    @Log(moduleName = "导出文章Markdown",operateType = OperateType.EXPORT)
    @PreAuthorize("@perm.hasPerm('admin:articles:export')")
    @GetMapping(value = "/admin/articles/exportAllToMarkdown")
    public CommonResponse<Object> exportAllToMarkdown() {
        return CommonResponse.success(articleService.exportAllToMarkdown());
    }

    @PreAuthorize("@perm.hasPerm('admin:articles:add')")
    @PostMapping(value = "/admin/articles/uploadImage")
    public CommonResponse<Object> uploadImage(@RequestParam("file") @Upload(allowType = "jpg,png,gif") MultipartFile file) {
        if (StringUtils.isNull(file) || file.isEmpty()) {
            throw new RequestFormatException("文件不能为空!");
        }
        return CommonResponse.success(articleService.uploadImage(file));
    }

}
