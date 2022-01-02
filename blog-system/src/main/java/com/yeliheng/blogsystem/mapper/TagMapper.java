package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Tag;

import java.util.List;

public interface TagMapper {

    int addTag(Tag tag);

    int updateTag(Tag tag);

    int checkIfExistArticle(Long tagId);

    int checkIfExistTag(Long tagId);

    List<Tag> selectTagList(Tag tag);

    int countArticles(Long tagId);

    int deleteById(Long tagId);

    int countTags();

}
