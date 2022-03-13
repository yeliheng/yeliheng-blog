package com.yeliheng.blogweb.controller;

import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogcommon.constant.BusinessType;
import com.yeliheng.blogsystem.domain.Dictionary;
import com.yeliheng.blogsystem.service.IDictionaryService;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class DictionaryController {
    @Autowired
    private IDictionaryService dictionaryService;

    @PreAuthorize("@perm.hasPerm('admin:dictionary:add')")
    @Log(moduleName = "添加字典",businessType = BusinessType.INSERT)
    @PostMapping("/admin/dictionary")
    public CommonResponse<Object> add(@Validated @RequestBody Dictionary dictionary) {
        dictionaryService.addDictionary(dictionary);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionary:delete')")
    @Log(moduleName = "删除字典",businessType = BusinessType.DELETE)
    @DeleteMapping("/admin/dictionary")
    public CommonResponse<Object> delete(@RequestParam Long id) {
        dictionaryService.deleteDictionary(id);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionary:edit')")
    @Log(moduleName = "修改字典",businessType = BusinessType.INSERT)
    @PutMapping("/admin/dictionary")
    public CommonResponse<Object> update(@RequestBody Dictionary dictionary) {
        dictionaryService.updateDictionary(dictionary);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionary:list')")
    @GetMapping("/admin/dictionary")
    public CommonResponse<Object> get( @RequestParam(value = "page",defaultValue = "1") Integer page,
                                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, Dictionary dictionary) {
        return CommonResponse.success(dictionaryService.getDictionaryList(page,pageSize,dictionary));
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionary:list')")
    @GetMapping("/admin/dictionaryById")
    public CommonResponse<Object> getById(@RequestParam Long id) {
        return CommonResponse.success(dictionaryService.getDictionaryById(id));
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionary:edit')")
    @PostMapping("/admin/dictionary/refreshCache")
    public CommonResponse<Object> refreshCache() {
        dictionaryService.refreshDictionaryCache();
        return CommonResponse.success();
    }
}
