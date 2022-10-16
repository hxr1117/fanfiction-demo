package com.example.ffdemo.controller;

import com.example.ffdemo.dto.ArticleDto;
import com.example.ffdemo.model.Article;
import com.example.ffdemo.model.Series;
import com.example.ffdemo.model.User;
import com.example.ffdemo.service.ArticleService;
import com.example.ffdemo.service.SeriesService;
import com.example.ffdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private UserService userService;

    public ArticleController(ArticleService articleService, UserService userService, SeriesService seriesService) {
        this.articleService = articleService;
        this.userService = userService;
        this.seriesService = seriesService;
    }

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    public ArticleController() {
    }

    @GetMapping("/new")
    public String newArticlePage(Model model) {
        model.addAttribute("title", "Post an Article");
        model.addAttribute("article", new ArticleDto());
        model.addAttribute("action", "article/new");

        return "newArticle";
    }

    @PostMapping("/new")
    public String saveArticle(@ModelAttribute("article") ArticleDto articleDto, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        articleDto.setUserId(userId);
        articleDto.setCreateTime(new Date(System.currentTimeMillis()));
        articleDto.setUpdateTime(new Date(System.currentTimeMillis()));

        articleService.saveArticle(articleDto);
        return "redirect:/article/user/" + userId;
    }

    @GetMapping("/user/{userId}")
    public String getUserArticles(Model model, @PathVariable(name = "userId") String userId) {
        List<Article> articles = articleService.getArticleByUserId(userId);
        model.addAttribute("articles", articles);

        model.addAttribute("username", userService.getUsernameById(userId));

        return "articles";
    }

    @GetMapping("/{id}")
    public String getArticle(@PathVariable String id, Model model, HttpSession session) {
        if (articleService.getArticleById(id).isPresent()) {
            Article article = articleService.getArticleById(id).get();
            String username = userService.getUsernameById(article.getUserId());

            if (article.getSeriesId() != null && seriesService.getSeriesById(article.getSeriesId()).isPresent()) {
                Series series = seriesService.getSeriesById(article.getSeriesId()).get();
                model.addAttribute("series", series);
                Map<String, String> chapters = new HashMap<>();
                for (String articleId: series.getArticleList()) {
                    if (articleService.getTitleById(id) != null) {
                        chapters.put(articleId, articleService.getTitleById(articleId));
                    }
                }
                model.addAttribute("chapters", chapters);
            }

            model.addAttribute("username", username);
            model.addAttribute("article", article);
            return "article";
        } else {
            return "main";
        }
    }

    @GetMapping("/search")
    public String searchArticles(Model model, @RequestParam(value = "title", required = false) String title,
                                 @RequestParam(value = "page", required = false) Integer page) {
        title = title != null ? title : "";
        page = page == null ? 0 : page;

        List<Article> articles = articleService.getArticleByTitle(title, page);

        model.addAttribute("type", "search");
        model.addAttribute("search", title);
        model.addAttribute("announcements", articles);
        return "announcements";
    }

    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable String id, HttpSession session) {
        articleService.deleteById(id);
        String userId = (String) session.getAttribute("userId");
        return "redirect:/article/user/" + userId;
    }

    @GetMapping("/edit/{id}")
    public String editArticle(@PathVariable String id, Model model) {
        Optional<Article> article = articleService.getArticleById(id);
        article.ifPresent(value -> model.addAttribute("article", value));
        model.addAttribute("action", "article/edit/" + id);
        model.addAttribute("id", id);
        model.addAttribute("title", "Edit");
        return "newArticle";
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable String id,
                             @ModelAttribute("article") ArticleDto articleDto,
                             HttpSession session,
                             Model model) {
        try {
            Article article = articleService.saveEdit(articleDto, id);
            model.addAttribute("article", article);
            model.addAttribute("status", "success");
        } catch (Exception e) {
            model.addAttribute("status", "error");
        }
        return "newArticle";
    }
}
