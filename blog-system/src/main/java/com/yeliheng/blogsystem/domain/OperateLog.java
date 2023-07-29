package com.yeliheng.blogsystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.time.LocalDateTime;

public class OperateLog {

    @Excel(name = "操作日志ID")
    private Long id;

    private Long userId;

    @Excel(name = "操作者",width = 20)
    private String username;

    @Excel(name = "模块名",width = 20)
    private String moduleName;

    @Excel(name = "操作类型", replace = {"其他_0", "插入_1", "删除_2", "修改_3", "查询_4", "导出_5"})
    private int operateType;

    @Excel(name = "操作者IP",width = 25)
    private String ip;

    @Excel(name = "URL",width = 20)
    private String url;

    @Excel(name = "请求方法")
    private String method;

    @Excel(name = "操作函数",width = 30)
    private String function;

    @Excel(name = "请求参数", width = 30)
    private String param;

    @Excel(name = "结果", width = 30)
    private String result;

    @Excel(name = "错误详情",width = 30)
    private String errorDetail;

    @Excel(name = "状态",replace = {"成功_1","失败_0"})
    private String status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErrorDetail() {
        return errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
