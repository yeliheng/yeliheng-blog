package com.yeliheng.blogsystem.service;


import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.domain.Draft;

public interface IDraftService {

    /**
     * 保存草稿文章
     * @param draft 草稿实体
     */
    void addDraft(Draft draft);

    /**
     * 更新草稿文章
     * @param draft 草稿实体
     */
    void updateDraft(Draft draft);

    /**
     * 删除草稿文章
     * @param draftId 草稿Id
     */
    void deleteDraft(Long draftId);

    /**
     * 获取草稿文章
     * @param draftId 草稿Id
     * @return 草稿文章实体
     */
    Draft getDraftById(Long draftId);

    /**
     * 获取分页的草稿
     * @param page 第几页
     * @param pageSize 每页显示多少
     * @return 草稿列表
     */
    PageInfo<Draft> listDraft(int page, int pageSize);

}
