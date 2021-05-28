package com.uncle.csdn.service.impl;

import com.uncle.csdn.dao.TagMapper;
import com.uncle.csdn.entity.Tag;
import com.uncle.csdn.service.TagService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TagServiceImpl implements TagService {


    @Autowired
    TagMapper tagMapper;

    public List<Tag> getAll() {
        return tagMapper.findTagAll();
    }


    @Override
    public int addTag(Tag tag) {
        tag.setBlogCount(0);
        tag.setCreateTime(new Date());
        return tagMapper.insertTag(tag);
    }

    @Override
    public PageInfo<Tag> getTagByCondition(Map<String, Object> map) {

        if (map == null) {
            List<Tag> list = tagMapper.findTagAll();
            return new PageInfo<Tag>(list);
        }

        List<Tag> list = tagMapper.findTagByCondition(map);
        return new PageInfo<Tag>(list);

    }

    @Override
    public int delTag(int id) {

        ArrayList ids = new ArrayList();
        ids.add(id);

        List<Tag> list = tagMapper.findTagByIds(ids);
        if (list == null || list.size() == 0) {
            return 0;
        }

        Tag c = list.get(0);
        c.setTagState(-1);

        return tagMapper.updateTag(c);


    }

    @Override
    public Tag getTag(int id) {
        ArrayList ids = new ArrayList();
        ids.add(id);
        List<Tag> list = tagMapper.findTagByIds(ids);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }
}
