package com.yeliheng.blogweb.controller;

import com.yeliheng.blogcommon.annotation.Log;
import com.yeliheng.blogcommon.constant.BusinessType;
import com.yeliheng.blogsystem.domain.OperateLog;
import com.yeliheng.blogsystem.service.IOperateLogService;
import com.yeliheng.blogweb.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperateLogController {

    @Autowired
    private IOperateLogService operateLogService;

    @PreAuthorize("@perm.hasPerm('admin:operatelog:view')")
    @GetMapping("/admin/operateLogs")
    public CommonResponse<Object> getOperateLogs(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                 @RequestParam(value = "pageSize",defaultValue = "30") Integer pageSize, OperateLog operateLog){
        return CommonResponse.success(operateLogService.getOperateLog(page, pageSize, operateLog));
    }

    @DeleteMapping("/admin/operateLogs")
    @PreAuthorize("@perm.hasPerm('admin:operatelog:delete')")
    @Log(moduleName = "删除操作日志", businessType = BusinessType.DELETE)
    public CommonResponse<Object> deleteOperateLogs(@RequestParam Long[] operateLogIds) {
        operateLogService.deleteOperateLog(operateLogIds);
        return CommonResponse.success();
    }

    @DeleteMapping("/admin/operateLogs/clear")
    @PreAuthorize("@perm.hasPerm('admin:operatelog:delete')")
    @Log(moduleName = "清空操作日志", businessType = BusinessType.DELETE)
    public CommonResponse<Object> clearOperateLogs() {
        operateLogService.clearOperateLog();
        return CommonResponse.success();
    }
}
