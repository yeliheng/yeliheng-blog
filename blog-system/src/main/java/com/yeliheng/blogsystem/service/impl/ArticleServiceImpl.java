package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.exception.InternalServerException;
import com.yeliheng.blogcommon.exception.NotFoundException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogcommon.utils.WordUtils;
import com.yeliheng.blogsystem.domain.AritcleTag;
import com.yeliheng.blogsystem.domain.Article;
import com.yeliheng.blogsystem.mapper.ArticleMapper;
import com.yeliheng.blogsystem.mapper.ArticleTagMapper;
import com.yeliheng.blogsystem.mapper.CategoryMapper;
import com.yeliheng.blogsystem.service.IArticleService;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements IArticleService {
    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private static final int ARTICLE_URL_LENGTH = 7;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private UserUtils userUtils;

    /**
     * 新增文章
     *
     * @param article 文章实体
     */
    @Override
    @Transactional
    public void addArticle(Article article) {
        article.setUserId(userUtils.getLoginUserId());
        int wordCount = WordUtils.wordCount(article.getContent());
        article.setWords(wordCount);
        article.setReadingTime(WordUtils.calReadingTimeByWords(wordCount));
        article.setUrl(generateUrl());
        if(StringUtils.isNotNull(article.getCategoryId()))
            if(categoryMapper.existsById(article.getCategoryId()) <= 0)
                throw new GeneralException("分类不存在，请修改后重新发布！");

        boolean result = articleMapper.addArticle(article);
        if(!result) throw new InternalServerException("发布文章失败，未知错误");
        //插入文章标签
        insertArticleTag(article);
    }

    /**
     * 删除文章
     *
     * @param articleId 文章Id
     */
    @Transactional
    @Override
    public void deleteAritcle(Long articleId) {
        if(articleMapper.deleteArticleById(articleId) <= 0)
            throw new GeneralException("删除失败，文章可能不存在");
        //删除文章与标签关联
        deleteArticleAllTags(articleId);
    }

    /**
     * 修改文章
     *
     * @param article   文章实体
     */
    @Transactional
    @Override
    public void updateArticle(Article article) {
        int wordCount = WordUtils.wordCount(article.getContent());
        article.setWords(wordCount);
        article.setReadingTime(WordUtils.calReadingTimeByWords(wordCount));
        if(StringUtils.isNotNull(article.getCategoryId()))
            if(categoryMapper.existsById(article.getCategoryId()) <= 0)
                throw new GeneralException("分类不存在，请修改后重新发布！");
        int rows = articleMapper.updateArticle(article);
        if(rows <= 0) throw new GeneralException("更新失败，文章可能不存在");
        //删除该文章的所有标签
        deleteArticleAllTags(article.getId());
        //插入标签
        insertArticleTag(article);
    }

    /**
     * 获取分页的文章
     *
     * @param page     第几页
     * @param pageSize 每页显示多少
     * @return 文章列表
     */
    @Override
    public PageInfo<Article> getArticles(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Article> articleList = articleMapper.getArticles();
        return new PageInfo<>(articleList);
    }

    /**
     * 获取后台分页的文章
     *
     * @param page     第几页
     * @param pageSize 每页显示多少
     * @return 文章列表
     */
    @Override
    public PageInfo<Article> getArticlesBacked(Integer page, Integer pageSize,Article article) {
        PageHelper.startPage(page,pageSize);
        List<Article> articleList = articleMapper.getArticlesBacked(article);
        return new PageInfo<>(articleList);
    }

    /**
     * 通过Id获取文章
     *
     * @param articleId 文章Id
     * @return 文章实体
     */
    @Override
    public Article getArticleById(Long articleId) {
        Article article = articleMapper.getArticleById(articleId);
        articleMapper.increaseViewsByArticleId(articleId);
        if(article == null)
            throw new NotFoundException("文章不存在");
        return article;
    }

    /**
     * 通过Url获取文章
     *
     * @param articleUrl 文章持久化Url
     * @return 文章实体
     */
    @Override
    public Article getArticleByUrl(String articleUrl) {
        Article article = articleMapper.getArticleByUrl(articleUrl);
        if(article == null)
            throw new NotFoundException("文章不存在");
        articleMapper.increaseViewsByArticleId(article.getId());
        return article;
    }

    /**
     * 通过Id获取文章-后台
     *
     * @param articleId 文章Id
     * @return 文章实体
     */
    @Override
    public Article getArticleByIdBacked(Long articleId) {
        return articleMapper.getArticleByIdBacked(articleId);
    }

    /**
     * 通过分类获取文章
     *
     * @param categoryId 分类Id
     * @return 文章列表
     */
    @Override
    public PageInfo<Article> getArticlesByCategory(Long categoryId,Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Article> articleList = articleMapper.getArticlesByCategoryId(categoryId);
        return new PageInfo<>(articleList);
    }

    /**
     * 通过标签获取文章
     *
     * @param tagId 标签Id
     * @param page 第几页
     * @param pageSize 每页显示多少
     * @return 文章列表
     */
    @Override
    public PageInfo<Article> getArticlesByTag(Long tagId, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Article> articleList = articleMapper.getArticlesByTagId(tagId);
        return new PageInfo<>(articleList);
    }


    /**
     *
     * 新增文章标签
     * @param article 文章实体
     */
    public void insertArticleTag(Article article){
        Long[] tags = article.getTagIds();
        if(StringUtils.isNotNull(tags)){
            //新增关联
            List<AritcleTag> list = new ArrayList<>();
            for(Long tagId : tags){
                AritcleTag aritcleTag = new AritcleTag();
                aritcleTag.setArticleId(article.getId());
                aritcleTag.setTagId(tagId);
                list.add(aritcleTag);
            }
            if(list.size() > 0){
                articleTagMapper.setArticleTags(list);
            }

        }
    }

    /**
     *
     * 删除某篇文章的所有标签
     * @param articleId 文章Id
     */
    public void deleteArticleAllTags(Long articleId){
        articleTagMapper.deleteArticleAllTags(articleId);
    }

    private String generateUrl() {
       return RandomStringUtils.random(ARTICLE_URL_LENGTH, "0123456789abcdef");
    }

}
