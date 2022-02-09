package com.yeliheng.blogsystem.service.impl;

import com.yeliheng.blogsystem.domain.OperateLog;
import com.yeliheng.blogsystem.mapper.OperateLogMapper;
import com.yeliheng.blogsystem.service.IOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperateLogServiceImpl implements IOperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Override
    public void insertLog(OperateLog operateLog) {
        operateLogMapper.insertLog(operateLog);
    }
}
