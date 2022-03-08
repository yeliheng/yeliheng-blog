package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.Dictionary;
import com.yeliheng.blogsystem.mapper.DictionaryMapper;
import com.yeliheng.blogsystem.service.IDictionaryService;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictionaryServiceImpl implements IDictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;
    @Autowired
    private UserUtils userUtils;

    /**
     * 添加字典
     *
     * @param dictionary 字典实体
     */
    @Override
    public void addDictionary(Dictionary dictionary) {
        if(!checkDictCodeUnique(dictionary))
            throw new GeneralException("字典代码已存在！");
        dictionary.setUserId(userUtils.getLoginUserId());
        dictionaryMapper.addDictionary(dictionary);
    }

    /**
     * 删除字典
     *
     * @param id 字典id
     */
    @Override
    public void deleteDictionary(long id) {
        if(dictionaryMapper.deleteById(id) <= 0)
           throw new GeneralException("删除失败，字典不存在");
    }

    /**
     * 更新字典
     *
     * @param dictionary 字典实体
     */
    @Override
    public void updateDictionary(Dictionary dictionary) {
        if(!checkDictCodeUnique(dictionary))
            throw new GeneralException("字典代码已存在！");
        dictionaryMapper.updateDictionary(dictionary);
    }

    /**
     * 获取分页的字典
     *
     * @param page       页码
     * @param pageSize   一页多少
     * @param dictionary 字典实体
     * @return 分页后的字典
     */
    @Override
    public PageInfo<Dictionary> getDictionaryList(Integer page, Integer pageSize, Dictionary dictionary) {
        PageHelper.startPage(page,pageSize);
        List<Dictionary> dictionaryList = dictionaryMapper.selectDictionaryList(dictionary);
        return new PageInfo<>(dictionaryList);
    }

    /**
     * 获取字典中的具体数据
     *
     * @param id 字典id
     */
    @Override
    public void getDictionaryById(long id) {

    }

    /**
     * 检查字典代码是否唯一
     *
     * @param dictionary 字典实体
     * @return true/false
     */
    @Override
    public boolean checkDictCodeUnique(Dictionary dictionary) {
        Long tempId = StringUtils.isNull(dictionary.getId()) ? -1L : dictionary.getId();
        Long id = dictionaryMapper.checkDictCodeUnique(dictionary);
        return StringUtils.isNull(id) || tempId.equals(id);
    }
}
