package com.yeliheng.blogweb.controller;

import com.yeliheng.blogsystem.domain.Draft;
import com.yeliheng.blogsystem.service.IDraftService;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class DraftController {

    @Autowired
    private IDraftService draftService;

    // 保存草稿
    @PreAuthorize("@perm.hasPerm('admin:articles:add')")
    @PostMapping(value = "/admin/draft")
    public CommonResponse<Object> draft(@RequestBody Draft draft) {
        draftService.addDraft(draft);
        return CommonResponse.success(draft.getId());
    }

    // 删除草稿
    @PreAuthorize("@perm.hasPerm('admin:articles:delete')")
    @DeleteMapping(value = "/admin/draft")
    public CommonResponse<Object> deleteDraft(@RequestParam("id") Long id) {
        draftService.deleteDraft(id);
        return CommonResponse.success();
    }

    // 获取草稿
    @PreAuthorize("@perm.hasPerm('admin:articles:get')")
    @GetMapping(value = "/admin/draft")
    public CommonResponse<Object> getDraft(@RequestParam("id") Long id) {
        return CommonResponse.success(draftService.getDraftById(id));
    }

    // 更新草稿
    @PreAuthorize("@perm.hasPerm('admin:articles:edit')")
    @PutMapping(value = "/admin/draft")
    public CommonResponse<Object> updateDraft(@RequestBody Draft draft) {
        draftService.updateDraft(draft);
        return CommonResponse.success();
    }

    // 分页获取草稿
    @PreAuthorize("@perm.hasPerm('admin:articles:list')")
    @GetMapping(value = "/admin/draft/list")
    public CommonResponse<Object> listDraft(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize) {
        return CommonResponse.success(draftService.listDraft(page, pageSize));
    }

}
