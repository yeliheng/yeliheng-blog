package com.yeliheng.blogweb.controller;

import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogcommon.constant.OperateType;
import com.yeliheng.blogsystem.domain.DictionaryData;
import com.yeliheng.blogsystem.service.IDictionaryDataService;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class DictionaryDataController {
    @Autowired
    private IDictionaryDataService dictionaryDataService;

    @PreAuthorize("@perm.hasPerm('admin:dictionarydata:add')")
    @Log(moduleName = "添加字典数据",operateType = OperateType.INSERT)
    @PostMapping("/admin/dictionaryData")
    public CommonResponse<Object> add(@Validated @RequestBody DictionaryData dictionaryData) {
        dictionaryDataService.addDictionaryData(dictionaryData);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionarydata:delete')")
    @Log(moduleName = "删除字典数据",operateType = OperateType.DELETE)
    @DeleteMapping("/admin/dictionaryData")
    public CommonResponse<Object> delete(@RequestParam Long id) {
        dictionaryDataService.deleteDictionaryData(id);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionarydata:edit')")
    @Log(moduleName = "修改字典数据",operateType = OperateType.INSERT)
    @PutMapping("/admin/dictionaryData")
    public CommonResponse<Object> update(@RequestBody DictionaryData dictionaryData) {
        dictionaryDataService.updateDictionaryData(dictionaryData);
        return CommonResponse.success();
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionarydata:list')")
    @GetMapping("/admin/dictionaryData")
    public CommonResponse<Object> get( @RequestParam(value = "page",defaultValue = "1") Integer page,
                                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize, DictionaryData dictionaryData) {
        return CommonResponse.success(dictionaryDataService.getDictionaryDataList(page,pageSize,dictionaryData));
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionarydata:list')")
    @GetMapping("/admin/dictionaryDataById")
    public CommonResponse<Object> getById(@RequestParam Long id) {
        return CommonResponse.success(dictionaryDataService.getDictionaryDataById(id));
    }

    @PreAuthorize("@perm.hasPerm('admin:dictionarydata:list')")
    @GetMapping("/admin/dictionaryDataByCode")
    public CommonResponse<Object> getByCode(@RequestParam String dictCode) {
        return CommonResponse.success(dictionaryDataService.getDictionaryDataByCode(dictCode));
    }

    @GetMapping("/admin/dictionaryData/{dictCode}")
    public CommonResponse<Object> getDictData(@PathVariable String dictCode) {
        return CommonResponse.success(dictionaryDataService.getDictionaryDataByCode(dictCode));
    }
}
