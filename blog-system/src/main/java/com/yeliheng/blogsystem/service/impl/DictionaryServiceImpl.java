package com.yeliheng.blogsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yeliheng.blogcommon.exception.GeneralException;
import com.yeliheng.blogcommon.utils.DictionaryUtils;
import com.yeliheng.blogcommon.utils.StringUtils;
import com.yeliheng.blogsystem.domain.Dictionary;
import com.yeliheng.blogsystem.domain.DictionaryData;
import com.yeliheng.blogsystem.mapper.DictionaryDataMapper;
import com.yeliheng.blogsystem.mapper.DictionaryMapper;
import com.yeliheng.blogsystem.service.IDictionaryService;
import com.yeliheng.blogsystem.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DictionaryServiceImpl implements IDictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Autowired
    private DictionaryDataMapper dictionaryDataMapper;

    @Autowired
    private UserUtils userUtils;

    @Autowired
    private DictionaryUtils<DictionaryData> dictionaryUtils;

    /**
     * 初始化字典缓存
     */
    @PostConstruct
    public void initDictionaryCache() {
        loadDictionaryCache();
    }

    /**
     * 将字典数据载入缓存
     */
    public void loadDictionaryCache() {
        List<Dictionary> dictionaryList = dictionaryMapper.selectDictionaryList(null);
        for(Dictionary dictionary : dictionaryList) {
            List<DictionaryData> dataList = dictionaryDataMapper.selectDictionaryDataByCode(dictionary.getDictCode());
            dictionaryUtils.setDictionaryCache(dictionary.getDictCode(), dataList);
        }
    }

    /**
     * 清空字典缓存
     */
    public void clearDictionaryCache() {
        dictionaryUtils.clearDictionaryCache();
    }

    /**
     * 刷新字典缓存
     */
    @Override
    public void refreshDictionaryCache() {
        dictionaryUtils.clearDictionaryCache();
        loadDictionaryCache();
    }

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
        int row = dictionaryMapper.addDictionary(dictionary);
        if(row > 0) {
            dictionaryUtils.setDictionaryCache(dictionary.getDictCode(),null);
        }
    }

    /**
     * 删除字典
     *
     * @param id 字典id
     */
    @Override
    public void deleteDictionary(long id) {
        Dictionary dictionary = dictionaryMapper.selectDictionaryById(id);
        if(StringUtils.isNull(dictionary))
            throw new GeneralException("删除失败，字典不存在");
        if(dictionaryDataMapper.countDictionaryDataByCode(dictionary.getDictCode()) > 0)
            throw new GeneralException("删除失败，字典下存在数据");
        if(dictionaryMapper.deleteById(id) <= 0)
           throw new GeneralException("删除失败，未知错误");
        dictionaryUtils.deleteDictionaryCache(dictionary.getDictCode());
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
        int row = dictionaryMapper.updateDictionary(dictionary);
        if(row > 0) {
            List<DictionaryData> dictionaryDataList = dictionaryDataMapper.selectDictionaryDataByCode(dictionary.getDictCode());
            dictionaryUtils.setDictionaryCache(dictionary.getDictCode(), dictionaryDataList);
        }
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
    public Dictionary getDictionaryById(long id) {
        return dictionaryMapper.selectDictionaryById(id);
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
