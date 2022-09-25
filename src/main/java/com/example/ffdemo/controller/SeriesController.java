package com.example.ffdemo.controller;

import com.example.ffdemo.dto.SeriesDto;
import com.example.ffdemo.model.Article;
import com.example.ffdemo.model.Series;
import com.example.ffdemo.service.ArticleService;
import com.example.ffdemo.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/series")
public class SeriesController {
    @Autowired
    private SeriesService seriesService;

    @Autowired
    private ArticleService articleService;

    public SeriesController(SeriesService service, ArticleService articleService) {
        this.seriesService = service;
        this.articleService = articleService;
    }

    public SeriesController() {
    }

    @GetMapping("/user/{userId}")
    public String getUserSeries(@PathVariable String userId, Model model) {
        List<Series> seriesList = (List<Series>) seriesService.getSeriesByUserId(userId);
        model.addAttribute("seriesList", seriesList);
        return "seriesList";
    }

    @GetMapping("/new")
    public String newSeriesPage(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        List<Article> articles = articleService.getArticleByUserId(userId);

        model.addAttribute("action", "series/new");
        model.addAttribute("series", new SeriesDto());
        model.addAttribute("articleList", articles);

        return "series";
    }

    @PostMapping("/new")
    public String newSeriesSubmit(@ModelAttribute("series") SeriesDto seriesDto, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        seriesDto.setUserId(userId);
        seriesService.saveSeries(seriesDto);
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

            return "series";
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
            return "redirect:/series/edit/" + id;
        }
        return "redirect:/";
    }
}
