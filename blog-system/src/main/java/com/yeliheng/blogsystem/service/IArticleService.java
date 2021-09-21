package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.entity.Article;

import java.util.List;


public interface IArticleService {

    /**
     *  新增文章
     * @param article 文章实体
     *
     *  */
    void addArticle(Article article);

    /**
     *  删除文章
     * @param articleId 文章Id
     *
     *  */
    void deleteAritcle(Integer articleId);

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
     * 通过Id获取文章
     * @param articleId 文章Id
     * @return 文章实体
     *
     * */
    Article getArticleById(Long articleId);

    /**
     *  通过分类获取文章
     * @param categoryId 分类Id
     * @return 文章列表
     *
     *  */
    List<Article> getArticlesByCategory(Integer categoryId);

    /**
     *  通过标签获取文章
     * @param tagId 标签Id
     * @return 文章列表
     *
     *  */
    List<Article> getArticlesByTag(Integer tagId);
}
