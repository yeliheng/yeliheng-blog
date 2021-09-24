package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.entity.AritcleTag;
import com.yeliheng.blogsystem.entity.Tag;
import com.yeliheng.blogsystem.exception.GeneralException;
import com.yeliheng.blogsystem.exception.InternalServerException;
import com.yeliheng.blogsystem.mapper.TagMapper;
import com.yeliheng.blogsystem.service.ITagService;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements ITagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private UserUtils userUtils;

    /**
     * 新增标签
     *
     * @param tag 标签实体
     */
    @Override
    public void addTag(Tag tag) {
        tag.setUserId(userUtils.getLoginUserId());
        int rows = tagMapper.addTag(tag);
        if(rows <= 0) throw new InternalServerException("添加失败，未知错误");
    }

    /**
     * 删除标签
     *
     * @param tagId 标签Id
     */
    @Override
    public void deleteTag(Long tagId) {
        int rows = tagMapper.deleteByPrimaryKey(tagId);
        if(rows <= 0) throw new GeneralException("删除失败，可能标签不存在");
    }

    /**
     * 更新标签
     *
     * @param tag 标签实体
     */
    @Override
    public void updateTag(Tag tag) {
        int rows = tagMapper.updateTag(tag);
        if(rows <= 0) throw new GeneralException("更新失败，可能标签不存在");
    }

    /**
     * 获取所有标签
     *
     * @return 标签列表
     */
    @Override
    public List<Tag> getTags() {
        return tagMapper.selectAll();
    }

    /**
     * 设置文章标签列表
     *
     * @param articleTagList 文章标签实体
     */
    @Override
    public void setArticleTags(List<AritcleTag> articleTagList) {
        int rows = tagMapper.setArticleTags(articleTagList);
        if(rows <= 0) throw new GeneralException("设置标签失败");
    }


}
