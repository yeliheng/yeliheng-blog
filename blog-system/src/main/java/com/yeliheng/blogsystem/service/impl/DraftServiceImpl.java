package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogsystem.domain.Draft;
import com.yeliheng.blogsystem.mapper.DraftMapper;
import com.yeliheng.blogsystem.service.IDraftService;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftServiceImpl implements IDraftService {

    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private DraftMapper draftMapper;

    @Autowired
    private UserUtils userUtils;
    /**
     * 发布草稿文章
     *
     * @param draft 草稿实体
     */
    @Override
    public Long addDraft(Draft draft) {
        draft.setUserId(userUtils.getLoginUserId());
        return draftMapper.addDraft(draft);
    }

    /**
     * 更新草稿文章
     *
     * @param draft 草稿实体
     */
    @Override
    public void updateDraft(Draft draft) {
        if(draft.getDraftId() == null)
            throw new GeneralException("草稿Id不能为空");
        int rows = draftMapper.updateDraft(draft);
        if(rows <= 0) throw new GeneralException("更新失败，草稿可能不存在");
    }

    /**
     * 删除草稿文章
     *
     * @param draftId 草稿Id
     */
    @Override
    public void deleteDraft(Long draftId) {
        if(draftMapper.deleteDraftById(draftId) <= 0)
            throw new GeneralException("删除失败，文章可能不存在");
    }

    /**
     * 获取草稿文章
     *
     * @param draftId 草稿Id
     * @return 草稿文章实体
     */
    @Override
    public Draft getDraftById(Long draftId) {
        return draftMapper.getDraftById(draftId);
    }

    @Override
    public PageInfo<Draft> listDraft(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        List<Draft> draftList = draftMapper.getDraftList();
        return new PageInfo<>(draftList);
    }
}
