package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.utils.ExcelUtils;
import com.yeliheng.blogsystem.domain.Article;
import com.yeliheng.blogsystem.domain.OperateLog;
import com.yeliheng.blogsystem.mapper.OperateLogMapper;
import com.yeliheng.blogsystem.service.IOperateLogService;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperateLogServiceImpl implements IOperateLogService {
    @Autowired
    private OperateLogMapper operateLogMapper;
    @Autowired
    private ExcelUtils excelUtils;

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

    /**
     * 导出操作日志
     *
     * @param operateLog 操作日志筛选
     * @return excel路径
     */
    @Override
    public String exportOperateLog(OperateLog operateLog) {
        List<OperateLog> operateLogList =  operateLogMapper.getOperateLog(operateLog);
        ExportParams exportParams = new ExportParams();
        exportParams.setTitle("操作日志");
        exportParams.setSheetName("操作日志");
        return excelUtils.exportExcel(exportParams,operateLogList,OperateLog.class);
    }
}
