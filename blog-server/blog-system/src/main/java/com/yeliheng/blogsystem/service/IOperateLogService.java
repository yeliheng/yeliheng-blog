package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.domain.OperateLog;

import java.util.List;

public interface IOperateLogService {

    void insertLog(OperateLog operateLog);

    PageInfo<OperateLog> getOperateLog(Integer page, Integer pageSize, OperateLog operateLog);

    void deleteOperateLog(Long[] operateLogIds);

    void clearOperateLog();

    /**
     * 导出操作日志
     * @param operateLog 操作日志筛选
     * @return excel路径
     */
    String exportOperateLog(OperateLog operateLog);

}
