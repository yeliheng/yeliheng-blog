package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.domain.Article;
import com.yeliheng.blogsystem.vo.ArticleVo;
import org.springframework.web.multipart.MultipartFile;


public interface IArticleService {

    /**
     *  新增文章
     * @param articleVo 文章实体
     *
     *  */
    Long addArticle(ArticleVo articleVo);

    /**
     *  删除文章
     * @param articleId 文章Id
     *
     *  */
    void deleteAritcle(Long articleId);

    /**
     *  修改文章
     * @param article 文章实体
     *
     *  */
    void updateArticle(Article article);

    /**
     *  获取分页的文章
     * @param page 第几页
     * @param pageSize 每页显示多少
     * @return 文章列表
     *
     *  */
    PageInfo<Article> getArticles(Integer page, Integer pageSize);

    /**
     *  获取后台分页的文章
     * @param page 第几页
     * @param pageSize 每页显示多少
     * @return 文章列表
     *
     *  */
    PageInfo<Article> getArticlesBacked(Integer page, Integer pageSize,Article article);

    /**
     * 通过Id获取文章
     * @param articleId 文章Id
     * @return 文章实体
     *
     * */
    Article getArticleById(Long articleId);


    /**
     * 通过Url获取文章
     * @param articleUrl 文章持久化Url
     * @return 文章实体
     *
     * */
    Article getArticleByUrl(String articleUrl);


    /**
     * 通过Id获取文章-后台
     * @param articleId 文章Id
     * @return 文章实体
     *
     * */
    Article getArticleByIdBacked(Long articleId);

    /**
     *  通过分类获取文章
     * @param categoryId 分类Id
     * @return 文章列表
     *
     *  */
    PageInfo<Article> getArticlesByCategory(Long categoryId, Integer page, Integer pageSize);

    /**
     *  通过标签获取文章
     * @param tagId 标签Id
     * @return 文章列表
     *
     *  */
    PageInfo<Article> getArticlesByTag(Long tagId, Integer page, Integer pageSize);

    /**
     * 导出文章
     * @param article 文章筛选条件
     * @return 导出的excel路径
     */
    String exportArticle(Article article);

    /**
     * 导出所有文章为Markdown
     */
    String exportAllToMarkdown();

    /**
     * 上传文章图片
     * @param file 图片文件
     */
    String uploadImage(MultipartFile file);

}
