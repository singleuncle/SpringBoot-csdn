package com.uncle.csdn.controller.index;

import cn.hutool.core.convert.Convert;
import com.uncle.csdn.entity.Blog;
import com.uncle.csdn.entity.Columnist;
import com.uncle.csdn.entity.Tag;
import com.uncle.csdn.service.BlogService;
import com.uncle.csdn.service.ColumnistService;
import com.uncle.csdn.service.TagService;
import com.uncle.csdn.utils.MarkdownUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class IndexNavController {

    @Autowired
    ColumnistService columnistService;

    @Autowired
    TagService tagService;

    @Autowired
    BlogService blogService;

    @GetMapping("/index")
    public String indexPage(Model model) {

        // 首页的专栏显示10个
        List<Columnist> cList = columnistService.getColumnistByTop();
        model.addAttribute("cList", cList);

        List<Tag> tagsList = tagService.getAll();
        model.addAttribute("tList", tagsList);

        // 默认显示8条博客
        // 参数一：第N次
        // 参数二：每页多少条数据
        PageHelper.startPage(1, 8);
        PageInfo<Blog> pageInfo = blogService.getBlogByCondition(null);

        List<Blog> newest = blogService.findBlogNewest();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("newest", newest);
        model.addAttribute("blogNav", 1);

        return "index";
    }

    // 博客的条件查询
    @GetMapping("/getPaging")
    public String getPaging(@RequestParam Map<String, Object> map, Model model) {


        int pageNum = Convert.toInt(map.get("pageNum"));
        PageHelper.startPage(pageNum, 8);

        PageInfo<Blog> pageInfo = blogService.getBlogByCondition(map);

        model.addAttribute("pageInfo", pageInfo);

        // 返回指定模板片段
        return "index::table_refresh";
    }

    @GetMapping("/blogPage/{id}")
    public String blogPage(@PathVariable Integer id, Model model) {

        Blog blog = blogService.getBlog(id);

        // 格式转换
        blog.setContent(MarkdownUtil.markdownToHtmlExtens(blog.getContent()));
        model.addAttribute("blog", blog);

        return "blog";

    }

}
