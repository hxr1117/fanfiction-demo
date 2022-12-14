package com.example.ffdemo.controller;

import com.example.ffdemo.model.Article;
import com.example.ffdemo.model.User;
import com.example.ffdemo.service.ArticleService;
import com.example.ffdemo.service.SeriesService;
import com.example.ffdemo.service.UserService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
public class MainController {
    @Autowired
    private UserService userService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private ArticleService articleService;

    public MainController(UserService userService, ArticleService articleService, SeriesService seriesService) {
        this.userService = userService;
        this.articleService = articleService;
        this.seriesService = seriesService;
    }

    @GetMapping("/")
    public String main(Model model, HttpSession session, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String userId = authentication.getName();
            User user = userService.getUserById(userId);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", userId);
        }

        List<Article> announcements = articleService.getArticleByType("ANNOUNCEMENT", 0).toList();
        if (announcements.size() > 10) {
            announcements = announcements.subList(0, 10);
        }
        for (Article announcement : announcements) {
            announcement.setContent(Jsoup.parse(announcement.getContent()).text());
        }

        model.addAttribute("announcements", announcements);
        return "main";
    }

    @GetMapping("search/")
    public String searchOutput(Model model, @RequestParam(value = "title", required = true) String title) {
        return null;
    }
}
