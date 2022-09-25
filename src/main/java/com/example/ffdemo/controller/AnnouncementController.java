package com.example.ffdemo.controller;

import com.example.ffdemo.dto.ArticleDto;
import com.example.ffdemo.model.Article;
import com.example.ffdemo.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    private ArticleService articleService;

    public AnnouncementController(ArticleService articleService) {
        this.articleService = articleService;
    }

    public AnnouncementController() {
    }

    @GetMapping("/new")
    public String newPage(Model model) {
        model.addAttribute("title", "Post an Announcement");
        model.addAttribute("article", new ArticleDto());
        model.addAttribute("action", "announcement/new");

        return "newArticle";
    }

    @PostMapping("/new")
    public String newSubmit(@ModelAttribute("article") ArticleDto articleDto) {
        articleDto.setCreateTime(new Date(System.currentTimeMillis()));
        articleDto.setUpdateTime(new Date(System.currentTimeMillis()));
        articleService.saveAnnouncement(articleDto);

        // Todo: redirect to list page
        return "redirect:/announcement/";
    }

    @GetMapping("/edit/{id}")
    public String editPage(Model model, @PathVariable String id) {
        model.addAttribute("title", "Edit");
        model.addAttribute("article", new ArticleDto());
        model.addAttribute("action", "announcement/edit/" + id);

        return "newArticle";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@ModelAttribute("article") ArticleDto articleDto, @PathVariable String id) {
        articleDto.setUpdateTime(new Date(System.currentTimeMillis()));
        articleService.saveEdit(articleDto, id);

        return "redirect:/announcement/";
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Article> articleList = articleService.getArticleByType("ANNOUNCEMENT");

        model.addAttribute("type", "announcement");
        model.addAttribute("announcements", articleList);

        return "announcements";
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable String id, Model model) {
        if (articleService.getArticleById(id).isPresent()) {
            Article article = articleService.getArticleById(id).get();
            model.addAttribute("article", article);
            return "article";
        }
        return "redirect:/";
    }
}
