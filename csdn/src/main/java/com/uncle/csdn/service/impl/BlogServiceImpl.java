package com.uncle.csdn.service.impl;

import com.uncle.csdn.dao.BlogMapper;
import com.uncle.csdn.dao.ColumnistMapper;
import com.uncle.csdn.dao.TagMapper;
import com.uncle.csdn.entity.Blog;
import com.uncle.csdn.entity.Columnist;
import com.uncle.csdn.entity.Tag;
import com.uncle.csdn.service.BlogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    ColumnistMapper columnistMapper;

    @Autowired
    TagMapper tagMapper;


    private boolean updateCount(Blog blog, int count) {

        ArrayList ids = new ArrayList();
        ids.add(blog.getColumnId());
        List<Columnist> list = columnistMapper.findColumnistByIds(ids);
        if (list == null || list.size() == 0) {
            // 没有对应的专栏
            return false;
        }

        Columnist col = list.get(0);
        int blogCount = col.getBlogCount();
        col.setBlogCount(blogCount + count);
        columnistMapper.updateColumnist(col);


        // 2选择对应标签，更对应标签的博客数量，一对多（Tag）
        // 1,3,5
        String tags = blog.getTags();
        List tagIds = Arrays.asList(tags.split(","));
        List<Tag> tagList = tagMapper.findTagByIds(tagIds);
        if (tagList == null || tagList.size() == 0) {
            // 没有对应的标签
            return false;
        }
        for (Tag tag : tagList) {
            int bc = tag.getBlogCount();
            tag.setBlogCount(bc + count);
            tagMapper.updateTag(tag);
        }

        return true;
    }

    // 添加事物，保证数据的一致性
    @Override
    @Transactional
    public int addBlog(Blog blog) {


        if (blog.getBlogState() == 1) {
            // 直接保存+发布
            blog.setPublishDate(new Date());

            // 1选择对应专栏，更对应专栏的博客数量，一对一（Columnist）
            // 只有发布状态的博客，才更新对应的数量+1
            if (!updateCount(blog, 1)) {
                // 没有对应的专栏或标签
                return -2;
            }
        }

        // 创建时间
        blog.setCreateTime(new Date());
        // 最后修改时间
        blog.setUpdateTime(new Date());
        return blogMapper.insertBlog(blog);
    }

    @Override
    public List<Blog> findBlogNewest() {
        return blogMapper.findBlogNewest();
    }

    private void getBlogColumnist(List<Blog> bList) {
        if (bList == null || bList.size() == 0) {

            return;
        }
        for (Blog blog : bList) {
            ArrayList ids = new ArrayList();
            ids.add(blog.getColumnId());
            List<Columnist> list = columnistMapper.findColumnistByIds(ids);
            if (list == null || list.size() == 0) {
                // 没有对应的专栏
                return;
            }

            Columnist col = list.get(0);
            blog.setColumnist(col);
        }
    }

    @Override
    public PageInfo<Blog> getBlogByCondition(Map<String, Object> map) {

        if (map == null) {

            // 调用查询之前，必须设置
            //  PageHelper.startPage(2, 2);
            // 哪一页，每页多少数据，就可以自动完成sql分页功能
            List<Blog> list = blogMapper.findBlogAll();
            getBlogColumnist(list);
            return new PageInfo<Blog>(list);

        }

        List<Blog> list = blogMapper.findBlogByCondition(map);
        getBlogColumnist(list);
        return new PageInfo<Blog>(list);
    }

    @Override
    public int delBlog(int id) {

        ArrayList ids = new ArrayList();
        ids.add(id);

        List<Blog> list = blogMapper.findBlogByIds(ids);
        if (list == null || list.size() == 0) {
            return 0;
        }

        Blog blog = list.get(0);
        blog.setBlogState(-1);

        return blogMapper.updateBlog(blog);
    }

    @Override
    public Blog getBlog(int id) {
        ArrayList ids = new ArrayList();
        ids.add(id);
        List<Blog> list = blogMapper.findBlogByIds(ids);
        if (list == null || list.size() == 0) {
            return null;
        }
        getBlogColumnist(list);
        return list.get(0);
    }

    @Override
    public int updateBlog(Blog blog) {
        // TODO 专栏或标签对应下的博客数量，在草稿状态下，数量更新问题
        return blogMapper.updateBlog(blog);
    }
}
