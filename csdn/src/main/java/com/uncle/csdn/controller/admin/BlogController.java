package com.uncle.csdn.controller.admin;


import cn.hutool.core.convert.Convert;
import com.uncle.csdn.entity.Blog;
import com.uncle.csdn.entity.Columnist;
import com.uncle.csdn.entity.Tag;
import com.uncle.csdn.service.BlogService;
import com.uncle.csdn.service.ColumnistService;
import com.uncle.csdn.service.TagService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BlogController {
    @Autowired
    ColumnistService columnistService;

    @Autowired
    TagService tagService;

    @Autowired
    BlogService blogService;


    @GetMapping("/blogAddPage")
    public String blogAddPage(Model model) {
        // 专栏
        List<Columnist> cList = columnistService.getAll();
        // 标签
        List<Tag> tList = tagService.getAll();

        model.addAttribute("cList", cList);
        model.addAttribute("tList", tList);

        return "admin/blog_add";
    }

    @PostMapping("/blogAdd")
    public String blogAdd(Blog blog) {

        int code = blogService.addBlog(blog);

        if (code < 1) {
            // 添加博客失败
        }

        return "redirect:/admin/index";
    }

    // 删除博客
    // 使用rest的delete请求，默认是关闭的，需要开启
    @DeleteMapping("/delBlog")
    public String delBlog(@RequestParam Map<String, Object> map, Model model) {

        int id = Convert.toInt(map.get("id"));
        // 删除对应博客
        int code = blogService.delBlog(id);

        int pageNum = Convert.toInt(map.get("pageNum"));
        PageHelper.startPage(pageNum, 8);

        PageInfo<Blog> pageInfo = blogService.getBlogByCondition(map);
        model.addAttribute("pageInfo", pageInfo);

        return "admin/manage::table_refresh";
    }

    @GetMapping("/editBlog/{id}")
    public String editBlog(@PathVariable int id, Model model) {
        Blog blog = blogService.getBlog(id);
        if (blog == null) {
//            return ;
        }
        // 专栏
        List<Columnist> cList = columnistService.getAll();
        // 标签
        List<Tag> tList = tagService.getAll();

        model.addAttribute("cList", cList);
        model.addAttribute("tList", tList);

        model.addAttribute("blog", blog);
        return "admin/blog_edit";
    }


    @PostMapping("/blogUpdate")
    public String blogUpdate(Blog blog) {

        System.out.println(blog);

        int code = blogService.updateBlog(blog);

        return "redirect:/admin/index";
    }

    // 博客的条件查询
    @GetMapping("/findCondition")
    public String findByCondition(@RequestParam Map<String, Object> map, Model model) {


        int pageNum = Convert.toInt(map.get("pageNum"));
        PageHelper.startPage(pageNum, 8);

        PageInfo<Blog> pageInfo = blogService.getBlogByCondition(map);

        model.addAttribute("pageInfo", pageInfo);

        // "admin/manage::table_refresh"
        // 返回指定模板片段
        return "admin/manage::table_refresh";
    }
}
