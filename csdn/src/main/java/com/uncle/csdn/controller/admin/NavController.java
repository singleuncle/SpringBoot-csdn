package com.uncle.csdn.controller.admin;

import com.uncle.csdn.entity.Blog;
import com.uncle.csdn.entity.Columnist;
import com.uncle.csdn.entity.Comment;
import com.uncle.csdn.entity.Tag;
import com.uncle.csdn.service.BlogService;
import com.uncle.csdn.service.ColumnistService;
import com.uncle.csdn.service.CommentService;
import com.uncle.csdn.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class NavController {

    @Autowired
    ColumnistService columnistService;

    @Autowired
    TagService tagService;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentService commentService;

    /**
     * 后台首页
     *
     * @return
     */
    @GetMapping("/index")
    public String index(Model model) {
        // 获取博客数据
        // 专栏
        List<Columnist> cList = columnistService.getAll();
        model.addAttribute("cList", cList);
        // 默认显示8条博客
        // 参数一：第N次
        // 参数二：每页多少条数据
        PageHelper.startPage(1, 8);
        PageInfo<Blog> pageInfo = blogService.getBlogByCondition(null);
        List<Blog> blogList = pageInfo.getList();
        model.addAttribute("blogList", blogList);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("navIndex", 1);

        return "admin/manage";
    }

    @GetMapping("/commentPage")
    public String commentPage(Model model) {

        // 控制导航的index选中位置
        model.addAttribute("navIndex", 4);

        PageHelper.startPage(1, 8);

        // 标签
        PageInfo<Comment> pageInfo = commentService.getCommentByCondition(null);

        model.addAttribute("pageInfo", pageInfo);

        return "admin/comments";
    }

    @GetMapping("/tagPage")
    public String tagPage(Model model) {

        // 控制导航的index选中位置
        model.addAttribute("navIndex", 3);

        PageHelper.startPage(1, 8);

        // 标签
        PageInfo<Tag> pageInfo = tagService.getTagByCondition(null);

        model.addAttribute("pageInfo", pageInfo);

        return "admin/tags";
    }

    @GetMapping("/columnistPage")
    public String columnistPage(Model model) {

        model.addAttribute("navIndex", 2);

        PageHelper.startPage(1, 8);

        // 专栏
        PageInfo<Columnist> pageInfo = columnistService.getColumnistByCondition(null);

        model.addAttribute("pageInfo", pageInfo);

        return "admin/columnist";
    }
}
