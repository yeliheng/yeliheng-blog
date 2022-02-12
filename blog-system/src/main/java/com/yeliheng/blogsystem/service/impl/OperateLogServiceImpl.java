package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.domain.OperateLog;
import com.yeliheng.blogsystem.mapper.OperateLogMapper;
import com.yeliheng.blogsystem.service.IOperateLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements IOperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Override
    public void insertLog(OperateLog operateLog) {
        operateLogMapper.insertLog(operateLog);
    }

    @Override
    public PageInfo<OperateLog> getOperateLog(Integer page, Integer pageSize, OperateLog operateLog) {
        PageHelper.startPage(page,pageSize);
        List<OperateLog> operateLogList = operateLogMapper.getOperateLog(operateLog);
        return new PageInfo<>(operateLogList);
    }

    @Override
    public void deleteOperateLog(Long[] operateLogIds) {
        operateLogMapper.deleteOperateLogByIds(operateLogIds);
    }

    @Override
    public void clearOperateLog() {
        operateLogMapper.clearOperateLog();
    }
}
