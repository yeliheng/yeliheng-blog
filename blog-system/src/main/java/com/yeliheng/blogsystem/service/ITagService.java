package com.yeliheng.blogsystem.service;

import com.yeliheng.blogsystem.entity.Tag;

import java.util.List;

public interface ITagService {

    /**
     *
     * 新增标签
     * @param tag 标签实体
     */
    void addTag(Tag tag);

    /**
     *
     * 删除标签
     * @param tagId 标签Id
     */
    void deleteTag(Long tagId);

    /**
     *
     * 更新标签
     * @param tag 标签实体
     */
    void updateTag(Tag tag);

    /**
     *
     * 获取所有标签
     * @return 标签列表
     */
    List<Tag> getTags();
}
