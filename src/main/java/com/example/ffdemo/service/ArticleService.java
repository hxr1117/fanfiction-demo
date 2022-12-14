package com.example.ffdemo.service;

import com.example.ffdemo.dto.ArticleDto;
import com.example.ffdemo.model.Article;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article saveAnnouncement(ArticleDto articleDto);
    Article saveArticle(ArticleDto articleDto);
    Page<Article> getArticleByTitle(String title, Integer page);
    Optional<Article> getArticleById(String id);
    List<Article> getArticleByUserId(String userId);

    void deleteById(String id);

    Article saveEdit(ArticleDto articleDto, String id);
    void setType(String id, String type);
    Page<Article> getArticleByType(String type, Integer page);
    void setSeriesId(String seriesId, List<String> articleId);
    String getTitleById(String id);
}
