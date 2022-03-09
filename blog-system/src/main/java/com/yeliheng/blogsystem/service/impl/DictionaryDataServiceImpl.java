package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.DictionaryData;
import com.yeliheng.blogsystem.mapper.DictionaryDataMapper;
import com.yeliheng.blogsystem.service.IDictionaryDataService;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryDataServiceImpl implements IDictionaryDataService {

    @Autowired
    private UserUtils userUtils;
    @Autowired
    private DictionaryDataMapper dictionaryDataMapper;

    /**
     * 添加字典数据
     *
     * @param dictData 字典数据实体
     */
    @Override
    public void addDictionaryData(DictionaryData dictData) {
        if(!checkDictLabelUnique(dictData))
            throw new GeneralException("字典数据标签已存在！");
        if(!checkDictValueUnique(dictData))
            throw new GeneralException("字典数据值已存在！");
        dictData.setUserId(userUtils.getLoginUserId());
        dictionaryDataMapper.addDictionaryData(dictData);
    }

    /**
     * 删除字典数据
     *
     * @param id id
     */
    @Override
    public void deleteDictionaryData(long id) {
        if(dictionaryDataMapper.deleteById(id) <= 0)
            throw new GeneralException("删除失败，字典数据不存在");
    }

    /**
     * 更新字典数据
     *
     * @param dictData 字典数据实体
     */
    @Override
    public void updateDictionaryData(DictionaryData dictData) {
        if(!checkDictLabelUnique(dictData))
            throw new GeneralException("字典数据标签已存在！");
        if(!checkDictValueUnique(dictData))
            throw new GeneralException("字典数据值已存在！");
        dictionaryDataMapper.updateDictionaryData(dictData);
    }

    /**
     * 获取字典数据
     *
     * @param page     页码
     * @param pageSize 一页多少
     * @param dictData 字典数据实体
     * @return 字典数据分页
     */
    @Override
    public PageInfo<DictionaryData> getDictionaryDataList(Integer page, Integer pageSize, DictionaryData dictData) {
        PageHelper.startPage(page,pageSize);
        List<DictionaryData> dictionaryDataList = dictionaryDataMapper.selectDictionaryDataList(dictData);
        return new PageInfo<>(dictionaryDataList);
    }

    /**
     * 通过id获取字典数据
     *
     * @param id id
     */
    @Override
    public DictionaryData getDictionaryDataById(long id) {
        return dictionaryDataMapper.selectDictionaryDataById(id);
    }

    /**
     * 检查字典标签是否唯一
     *
     * @param dictData 字典数据
     * @return true/false
     */
    @Override
    public boolean checkDictLabelUnique(DictionaryData dictData) {
        Long tempId = StringUtils.isNull(dictData.getId()) ? -1L : dictData.getId();
        Long id = dictionaryDataMapper.checkDictLabelUnique(dictData);
        return StringUtils.isNull(id) || tempId.equals(id);
    }

    /**
     * 检查字典值是否唯一
     *
     * @param dictData 字典数据
     * @return true/false
     */
    @Override
    public boolean checkDictValueUnique(DictionaryData dictData) {
        Long tempId = StringUtils.isNull(dictData.getId()) ? -1L : dictData.getId();
        Long id = dictionaryDataMapper.checkDictValueUnique(dictData);
        return StringUtils.isNull(id) || tempId.equals(id);
    }
}
