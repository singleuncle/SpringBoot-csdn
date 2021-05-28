package com.uncle.csdn.dao;

import com.uncle.csdn.entity.Tag;

import java.util.List;
import java.util.Map;

public interface TagMapper {

    int insertTag(Tag tag);

    List<Tag> findTagByCondition(Map<String, Object> map);

    int updateTag(Tag tag);

    List<Tag> findTagAll();

    List<Tag> findTagByIds(List ids);

}
