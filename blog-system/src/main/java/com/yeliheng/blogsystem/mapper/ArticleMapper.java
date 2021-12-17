package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleMapper extends Mapper<Article> {
    
    boolean addArticle(Article article);

    int updateArticle(Article article);

    List<Article> getArticles();

    List<Article> getArticlesByCategoryId(Long categoryId);

    List<Article> getArticlesBacked(Article article);

    Article getArticleByIdBacked(Long articleId);

    Article getArticleById(Long articleId);

}
