package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.AritcleTag;

import java.util.List;

public interface ArticleTagMapper {
    int setArticleTags(List<AritcleTag> articleTagList);

    int deleteArticleAllTags(Long articleId);
}
