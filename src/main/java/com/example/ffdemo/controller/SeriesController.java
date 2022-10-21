package com.example.ffdemo.controller;

import com.example.ffdemo.dto.SeriesDto;
import com.example.ffdemo.model.Article;
import com.example.ffdemo.model.Series;
import com.example.ffdemo.service.ArticleService;
import com.example.ffdemo.service.SeriesService;
import com.example.ffdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserService userService;

    public SeriesController(SeriesService service,
                            ArticleService articleService,
                            UserService userService) {
        this.seriesService = service;
        this.articleService = articleService;
        this.userService = userService;
    }

    public SeriesController() {
    }

    @GetMapping("/user/{userId}")
    public String getUserSeries(@PathVariable String userId, Model model) {
        List<Series> seriesList = (List<Series>) seriesService.getSeriesByUserId(userId);
        String username = userService.getUsernameById(userId);
        model.addAttribute("seriesList", seriesList);
        model.addAttribute("username", username);
        return "seriesList";
    }

    @GetMapping("/new")
    public String newSeriesPage(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        List<Article> articles = articleService.getArticleByUserId(userId);

        model.addAttribute("action", "series/new");
        model.addAttribute("series", new SeriesDto());
        model.addAttribute("articleList", articles);

        return "newSeries";
    }

    @PostMapping("/new")
    public String newSeriesSubmit(@ModelAttribute("series") SeriesDto seriesDto, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        // TODO: Should I link the series to article?
        seriesDto.setUserId(userId);
        Series series = seriesService.saveSeries(seriesDto);
        List<String> articleList = (List<String>) seriesDto.getArticleList();

        articleService.setSeriesId(series.getId(), articleList);
        return "redirect:/series/user/" + userId;
    }

    @GetMapping("/edit/{id}")
    public String editSeriesPage(@PathVariable String id, Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (seriesService.getSeriesById(id).isPresent()) {
            System.out.println(">> " + userId);
            Series series = seriesService.getSeriesById(id).get();

            List<Boolean> inSeries = new ArrayList<>();
            List<Article> articles = articleService.getArticleByUserId(userId);

            for (Article article : articles) {
                System.out.println(article.getTitle());
                if (series.containArticle(article.getId())) {
                    inSeries.add(true);
                } else {
                    inSeries.add(false);
                }
            }

            model.addAttribute("id", id);
            model.addAttribute("action", "series/edit/" + id);
            model.addAttribute("series", series);
            model.addAttribute("articleList", articles);
            model.addAttribute("inSeries", inSeries);

            return "newSeries";
        }
        return "redirect:/series/user/" + userId;
    }

    @PostMapping("/edit/{id}")
    public String editSubmit(@PathVariable String id, @ModelAttribute("series") SeriesDto seriesDto) {
        if (seriesService.getSeriesById(id).isPresent()) {
            Series series = seriesService.getSeriesById(id).get();
            series.setName(seriesDto.getName());
            series.setArticleList(seriesDto.getArticleList());
            seriesService.saveSeries(seriesDto, id);
            // Edit the series_id within articles instance
            articleService.setSeriesId(id, (List<String>) seriesDto.getArticleList());
            return "redirect:/series/edit/" + id;
        }
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String detailPage(@PathVariable String id, Model model) {
        if (seriesService.getSeriesById(id).isPresent()) {
            Series series = seriesService.getSeriesById(id).get();
            String username = userService.getUsernameById(series.getUserId());

            Map<String, String> articleNames = new HashMap<>();
            for (String articleId : series.getArticleList()) {
                if (articleService.getArticleById(articleId).isPresent()) {
                    articleNames.put(articleId, articleService.getArticleById(articleId).get().getTitle());
                }
            }

            model.addAttribute("author", username);
            model.addAttribute("series", series);
            model.addAttribute("articleList", articleNames);
            return "series";
        }
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        if (seriesService.getSeriesById(id).isPresent()) {
            Series series = seriesService.getSeriesById(id).get();
            articleService.setSeriesId("", (List<String>) series.getArticleList());
        }
        seriesService.deleteById(id);
        return "redirect:/series/user/" + id;
    }

    @GetMapping("/search")
    public String searchPage(Model model,
                             @RequestParam(value = "title", required = false) String title,
                             @RequestParam(value = "page", required = false) Integer page) {
        title = title != null ? title : "";
        page = page == null ? 0 : page;


        Page<Series> seriesList = seriesService.getAllSeries(title, page);

        model.addAttribute("url", "/series/search");
        model.addAttribute("totalPage", seriesList.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("seriesList", seriesList.toList());
        model.addAttribute("search", title);
        return "seriesList";
    }
}
