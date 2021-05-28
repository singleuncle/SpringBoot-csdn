package com.uncle.csdn.service;

import com.uncle.csdn.entity.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CommentService {

    List<Comment> getAll();

    int addComment(Comment comment);

    PageInfo<Comment> getCommentByCondition(Map<String, Object> map);

    int delComment(int id);

    Comment getComment(int id);

    int updateComment(Comment tag);

}
