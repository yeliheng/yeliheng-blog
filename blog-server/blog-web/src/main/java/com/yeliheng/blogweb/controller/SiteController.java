package com.yeliheng.blogweb.controller;

import com.yeliheng.blogsystem.domain.SiteInfo;
import com.yeliheng.blogsystem.mapper.ArticleMapper;
import com.yeliheng.blogsystem.mapper.CategoryMapper;
import com.yeliheng.blogsystem.mapper.NoticeMapper;
import com.yeliheng.blogsystem.mapper.TagMapper;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteController {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private NoticeMapper noticeMapper;

    @GetMapping("/site/info")
    public CommonResponse<Object> getSiteInfo(){
        SiteInfo siteInfo = new SiteInfo();
        siteInfo.setArticlesCount(articleMapper.countArticles());
        siteInfo.setCategoriesCount(categoryMapper.countCategories());
        siteInfo.setTagsCount(tagMapper.countTags());
        siteInfo.setNotice(noticeMapper.getLatestNotice());
        return CommonResponse.success(siteInfo);
    }

}
