package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.domain.Dictionary;

import java.util.List;

public interface DictionaryMapper {

    public int addDictionary(Dictionary dictionary);

    public int updateDictionary(Dictionary dictionary);

    public int deleteById(long id);

    public List<Dictionary> selectDictionaryList(Dictionary dictionary);

    public Dictionary selectDictionaryById(long id);

    public Long checkDictCodeUnique(Dictionary dictionary);
}
