package com.uncle.csdn.service.impl;

import com.uncle.csdn.dao.ColumnistMapper;
import com.uncle.csdn.entity.Columnist;
import com.uncle.csdn.service.ColumnistService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ColumnistServiceImpl implements ColumnistService {

    @Autowired
    ColumnistMapper columnistMapper;

    @Override
    public List<Columnist> getAll() {
        return columnistMapper.findColumnistAll();
    }

    @Override
    public List<Columnist> getColumnistByTop() {
        return columnistMapper.findColumnistByTop();
    }

    @Override
    public Columnist getColumnist(int id) {

        ArrayList ids = new ArrayList();
        ids.add(id);
        List<Columnist> list = columnistMapper.findColumnistByIds(ids);
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public int delColumnist(int id) {

        ArrayList ids = new ArrayList();
        ids.add(id);

        List<Columnist> list = columnistMapper.findColumnistByIds(ids);
        if (list == null || list.size() == 0) {
            return 0;
        }

        Columnist c = list.get(0);
        c.setColumnistState(-1);
        c.setUpdateTime(new Date());

        return columnistMapper.updateColumnist(c);
    }


    @Override
    public PageInfo<Columnist> getColumnistByCondition(Map<String, Object> map) {

        if (map == null) {
            // 调用查询之前，必须设置
            //  PageHelper.startPage(2, 2);
            // 哪一页，每页多少数据，就可以自动完成sql分页功能
            List<Columnist> list = columnistMapper.findColumnistAll();
            return new PageInfo<Columnist>(list);
        }

        List<Columnist> list = columnistMapper.findColumnistByCondition(map);

        return new PageInfo<Columnist>(list);
    }

    @Override
    public int addColumnist(Columnist columnist) {

        columnist.setUpdateTime(new Date());

        return columnistMapper.insertColumnist(columnist);
    }

    @Override
    public int updateColumnist(Columnist columnist) {
        return columnistMapper.updateColumnist(columnist);
    }
}
