package com.yeliheng.blogsystem.service;

import com.github.pagehelper.PageInfo;
import com.yeliheng.blogsystem.domain.Dictionary;

public interface IDictionaryService {

    /**
     * 添加字典
     * @param dictionary 字典实体
     */
    void addDictionary(Dictionary dictionary);

    /**
     * 删除字典
     * @param id 字典id
     */
    void deleteDictionary(long id);

    /**
     * 更新字典
     * @param dictionary 字典实体
     */
    void updateDictionary(Dictionary dictionary);

    /**
     * 获取分页的字典
     * @param page 页码
     * @param pageSize 一页多少
     * @param dictionary 字典实体
     * @return 分页后的字典
     */
    PageInfo<Dictionary> getDictionaryList(Integer page, Integer pageSize, Dictionary dictionary);

    /**
     * 获取字典中的具体数据
     * @param id 字典id
     */
    Dictionary getDictionaryById(long id);

    /**
     * 检查字典代码是否唯一
     * @param dictionary 字典实体
     * @return true/false
     */
    boolean checkDictCodeUnique(Dictionary dictionary);
}
