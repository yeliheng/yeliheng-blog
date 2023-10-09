package com.yeliheng.blogcommon.utils;

import com.yeliheng.blogcommon.constant.Constants;
import com.yeliheng.blogcommon.constant.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class DictionaryUtils<T> {

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 设置字典缓存
     * @param key 缓存键
     * @param dictionaryDataList 字典数据
     */
    public void setDictionaryCache(String key, List<T> dictionaryDataList) {
        redisUtils.setCacheObject(getKey(key),dictionaryDataList);
    }

    /**
     * 获取字典缓存
     * @param key 键
     * @return 字典列表
     */
    public List<T> getDictionaryCache(String key) {
        Object dictionaryObject = redisUtils.getCacheObject(getKey(key));
        if(StringUtils.isNotNull(dictionaryObject)) {
            return (List<T>) dictionaryObject;
        }
        return null;
    }

    /**
     * 删除某个键的字典缓存
     * @param key 键
     */
    public void deleteDictionaryCache(String key) {
        redisUtils.deleteObject(getKey(key));
    }


    /**
     * 清除字典缓存
     */
    public void clearDictionaryCache() {
        Collection<String> keys = redisUtils.keys(String.format(RedisKeys.DICTIONARY_KEY,"*"));
        redisUtils.deleteObject(keys);
    }

    /**
     * 获取带后缀的字典键
     * @param key 不带后缀的key
     * @return 键
     */
    public String getKey(String key) {
        return String.format(RedisKeys.DICTIONARY_KEY, key);
    }


}
