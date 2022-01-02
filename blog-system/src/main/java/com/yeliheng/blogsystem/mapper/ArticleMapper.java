package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Article;

import java.util.List;

public interface ArticleMapper{
    
    boolean addArticle(Article article);

    int updateArticle(Article article);

    int increaseViewsByArticleId(Long articleId);

    List<Article> getArticles();

    List<Article> getArticlesByCategoryId(Long categoryId);

    List<Article> getArticlesByTagId(Long tagId);

    List<Article> getArticlesBacked(Article article);

    Article getArticleByIdBacked(Long articleId);

    Article getArticleById(Long articleId);

    Article getArticleByUrl(String url);

    int deleteArticleById(Long articleId);

    int countArticles();
}
