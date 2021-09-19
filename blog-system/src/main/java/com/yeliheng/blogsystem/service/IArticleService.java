package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.entity.Article;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IArticleService {

    /* 新增文章 */
    void addArticle(Article article);

    /* 删除文章 */
    void deleteAritcle(Integer articleId);

    /* 修改文章 */
    void updateArticle(Integer articleId,Article article);

    /* 获取分页的文章 */
    List<Article> getArticles(Integer page,Integer pageSize);

    /* 通过Id获取文章 */
    Article getArticleById(Integer articleId);

    /* 通过分类获取文章 */
    List<Article> getArticlesByCategory(Integer categoryId);

    /* 通过标签获取文章 */
    List<Article> getArticlesByTag(Integer tagId);
}
