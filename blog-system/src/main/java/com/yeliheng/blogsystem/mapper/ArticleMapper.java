package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleMapper extends Mapper<Article> {
    
    boolean addArticle(Article article);

    int updateArticle(Article article);

    List<Article> getArticles();

    List<Article> getArticlesByCategoryId(Long categoryId);

    int setTags(Long tagId);

}
