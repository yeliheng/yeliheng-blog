package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.domain.DictionaryData;

import java.util.List;

public interface IDictionaryDataService {

    /**
     * 添加字典数据
     * @param dictData 字典数据实体
     */
    void addDictionaryData(DictionaryData dictData);

    /**
     * 删除字典数据
     * @param id id
     */
    void deleteDictionaryData(long id);

    /**
     * 更新字典数据
     * @param dictData 字典数据实体
     */
    void updateDictionaryData(DictionaryData dictData);

    /**
     * 获取字典数据
     * @param page 页码
     * @param pageSize 一页多少
     * @param dictData 字典数据实体
     * @return 字典数据分页
     */
    PageInfo<DictionaryData> getDictionaryDataList(Integer page, Integer pageSize, DictionaryData dictData);

    /**
     * 通过id获取字典数据
     * @param id id
     */
    DictionaryData getDictionaryDataById(long id);

    /**
     * 通过dictCode获取字典数据
     * @param dictCode 字典代码
     * @return 字典数据
     */
    List<DictionaryData> getDictionaryDataByCode(String dictCode);

    /**
     * 检查字典标签是否唯一
     * @param dictData 字典数据
     * @return true/false
     */
    boolean checkDictLabelUnique(DictionaryData dictData);

    /**
     * 检查字典值是否唯一
     * @param dictData 字典数据
     * @return true/false
     */
    boolean checkDictValueUnique(DictionaryData dictData);

}
