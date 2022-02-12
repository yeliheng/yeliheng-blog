package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.OperateLog;

import java.util.List;

public interface OperateLogMapper {

    int insertLog(OperateLog operateLog);

    List<OperateLog> getOperateLog(OperateLog operateLog);

    void deleteOperateLogByIds(Long[] operateLogIds);

    int clearOperateLog();
}
