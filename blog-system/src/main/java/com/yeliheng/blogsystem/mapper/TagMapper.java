package com.yeliheng.blogsystem.mapper;

import com.yeliheng.blogsystem.entity.Tag;
import tk.mybatis.mapper.common.Mapper;

public interface TagMapper extends Mapper<Tag> {

    int addTag(Tag tag);

    int updateTag(Tag tag);

}
