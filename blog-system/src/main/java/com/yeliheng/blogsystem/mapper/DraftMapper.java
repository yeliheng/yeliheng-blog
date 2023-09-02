package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Draft;

import java.util.List;

public interface DraftMapper {

    boolean addDraft(Draft draft);

    int updateDraft(Draft draft);

    Draft getDraftById(Long draftId);

    int deleteDraftById(Long draftId);

    List<Draft> getDraftList();


}
