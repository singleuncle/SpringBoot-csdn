package com.uncle.csdn.dao;

import com.uncle.csdn.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogMapper {

    List<Blog> findBlogByCondition(Map<String, Object> map);

    int insertBlog(Blog blog);

    int updateBlog(Blog blog);

    List<Blog> findBlogAll();

    List<Blog> findBlogNewest();

    List<Blog> findBlogByIds(List ids);
}
