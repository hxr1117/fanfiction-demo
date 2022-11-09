package com.example.ffdemo.controller;

import com.example.ffdemo.dto.UserDto;
import com.example.ffdemo.model.Article;
import com.example.ffdemo.model.Series;
import com.example.ffdemo.model.User;
import com.example.ffdemo.repository.SeriesRepository;
import com.example.ffdemo.service.ArticleService;
import com.example.ffdemo.service.SeriesService;
import com.example.ffdemo.service.UserService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SeriesService seriesService;
    @Autowired
    private ArticleService articleService;

    public UserController(UserService userService, SeriesService seriesService, ArticleService articleService) {
        super();
        this.userService = userService;
        this.articleService = articleService;
        this.seriesService = seriesService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") UserDto userDto) {
        User user = userService.saveUser(userDto);
        if (user != null) {
            System.out.println("Created a new user: " + userDto.getUsername());
            return "redirect:/registration?success";
        }
        return "redirect:/registration?error";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String checkPassword(@ModelAttribute("user") UserDto userDto, HttpServletRequest request) {
        User user = userService.getUserByEmail(userDto.getEmail());

        if (Objects.equals(user.getPassword(), userDto.getPassword())) {
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("username", user.getUsername());

            return "redirect:/";
        }
        return "redirect:/login?error";
    }

    @GetMapping("/adminLogin")
    public String adminPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "login";
    }

    // Todo
    @PostMapping("/adminLogin")
    public String adminSubmit(@ModelAttribute("user") UserDto userDto, HttpServletRequest request) {
        User user = userService.getUserByEmail(userDto.getEmail());

        // TODO: Probably there are some problems
        if (Objects.equals(user.getPassword(), userDto.getPassword())) {
            request.getSession().setAttribute("userId", user.getId());
            request.getSession().setAttribute("username", user.getUsername());
        }
        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().setAttribute("userId", "");
        request.getSession().setAttribute("username", "");
        return "redirect:/";
    }

    @GetMapping("/user/{id}")
    public String userPage(Model model, @PathVariable String id) {
        User user = userService.getUserById(id);
        List<Series> seriesList = (List<Series>) seriesService.getSeriesByUserId(id);
        List<Article> articleList = articleService.getArticleByUserId(id);

        for (Article article : articleList) {
            article.setContent(Jsoup.parse(article.getContent()).text());
        }
        model.addAttribute("user", user);
        model.addAttribute("seriesList", seriesList);
        model.addAttribute("articleList", articleList);
        return "user";
    }
}
