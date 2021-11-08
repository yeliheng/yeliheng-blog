package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.entity.AritcleTag;
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
    List<Tag> getTags(Tag tag);


    /**
     *
     * 获取分页后的标签
     * @param page 第几页
     * @param pageSize 每页显示多少
     * @return 标签列表带分页
     */
    PageInfo<Tag> getTagsPaged(Integer page,Integer pageSize,Tag tag);

}
