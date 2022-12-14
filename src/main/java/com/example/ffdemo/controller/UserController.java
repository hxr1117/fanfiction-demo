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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDto.setPassword(encoder.encode(userDto.getPassword()));
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(userDto.getPassword(), user.getPassword())) {
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
        List<Series> seriesList = seriesService.getSeriesByUserId(id, 0).toList();
        List<Article> articleList = articleService.getArticleByUserId(id);

        for (Article article : articleList) {
            article.setContent(Jsoup.parse(article.getContent()).text());
        }
        model.addAttribute("user", user);
        model.addAttribute("seriesList", seriesList);
        model.addAttribute("articleList", articleList);
        return "user";
    }

    @GetMapping("/user/edit/{id}")
    public String userEditPage(Model model, @PathVariable String id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserById(id);
        if (!Objects.equals(user.getId(), auth.getName())) {
            return "redirect:/";
        }

        model.addAttribute("user", user);
        return "userEdit";
    }

    @PostMapping("/user/edit/{id}")
    public String userEditProfile(Model model, @PathVariable String id, @ModelAttribute("user") UserDto userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserById(id);
        if (Objects.equals(user.getId(), auth.getName())) {
            if (!Objects.equals(userDto.getEmail(), user.getEmail())) {
                if (userService.getUserByEmail(userDto.getEmail()) != null) {
                    return "redirect:/user/edit/" + id + "?error";
                }
            }
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userDto.setPassword(encoder.encode(userDto.getPassword()));
            userService.saveUser(userDto);
        } else {
            return "redirect:/";
        }

        return "redirect:/user/edit/"+id+"?success";
    }
}
