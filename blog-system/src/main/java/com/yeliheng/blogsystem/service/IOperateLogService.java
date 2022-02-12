package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.domain.OperateLog;

import java.util.List;

public interface IOperateLogService {

    void insertLog(OperateLog operateLog);

    PageInfo<OperateLog> getOperateLog(Integer page, Integer pageSize, OperateLog operateLog);

    void deleteOperateLog(Long[] operateLogIds);

    void clearOperateLog();

}
