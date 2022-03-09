package com.yeliheng.blogsystem.mapper;


import com.yeliheng.blogsystem.domain.DictionaryData;

import java.util.List;

public interface DictionaryDataMapper {

    public int addDictionaryData(DictionaryData dictData);

    public int updateDictionaryData(DictionaryData dictData);

    public int deleteById(long id);

    public List<DictionaryData> selectDictionaryDataList(DictionaryData dictData);

    public DictionaryData selectDictionaryDataById(long id);

    public Long checkDictLabelUnique(DictionaryData dictData);

    public Long checkDictValueUnique(DictionaryData dictData);

}
