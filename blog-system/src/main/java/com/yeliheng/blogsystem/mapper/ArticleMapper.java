package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.Article;
import tk.mybatis.mapper.common.Mapper;

public interface ArticleMapper extends Mapper<Article> {
    boolean addArticle(Article article);

    int updateArticle(Article article);

}
