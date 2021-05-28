package com.uncle.csdn.service;

import com.uncle.csdn.entity.Blog;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BlogService {

    int addBlog(Blog blog);

    List<Blog> findBlogNewest();

    PageInfo<Blog> getBlogByCondition(Map<String, Object> map);

    int delBlog(int id);

    Blog getBlog(int id);

    int updateBlog(Blog blog);
}
