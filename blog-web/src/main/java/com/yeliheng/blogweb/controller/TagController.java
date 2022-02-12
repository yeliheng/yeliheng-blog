package com.yeliheng.blogweb.controller;

import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogcommon.constant.BusinessType;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.Tag;
import com.yeliheng.blogsystem.service.ITagService;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TagController {
    @Autowired
    private ITagService tagService;

    @PreAuthorize("@perm.hasPerm('admin:tags:add')")
    @Log(moduleName = "添加标签",businessType = BusinessType.INSERT)
    @PostMapping("/admin/tags")
    public CommonResponse<Object> add(@Validated @RequestBody Tag tag){
        tagService.addTag(tag);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:tags:delete')")
    @Log(moduleName = "删除标签",businessType = BusinessType.DELETE)
    @DeleteMapping("/admin/tags")
    public CommonResponse<Object> delete(@RequestParam Long id){
        tagService.deleteTag(id);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:tags:edit')")
    @Log(moduleName = "修改标签",businessType = BusinessType.UPDATE)
    @PutMapping("/admin/tags")
    public CommonResponse<Object> update(@RequestBody Tag tag){
        tagService.updateTag(tag);
        return CommonResponse.success();
    }

    @GetMapping("/tags")
    public CommonResponse<Object> getTags(@RequestParam(value = "page",required = false) Integer page,
                                             @RequestParam(value = "pageSize",required = false) Integer pageSize,Tag tag){
        if(StringUtils.isNull(page) || StringUtils.isNull(pageSize))
            return CommonResponse.success(tagService.getTags(tag));
        else return CommonResponse.success(tagService.getTagsPaged(page,pageSize,tag));
    }
}
