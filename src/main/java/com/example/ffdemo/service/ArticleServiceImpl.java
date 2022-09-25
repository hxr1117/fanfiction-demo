package com.example.ffdemo.service;

import com.example.ffdemo.dto.ArticleDto;
import com.example.ffdemo.model.Article;
import com.example.ffdemo.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article saveAnnouncement(ArticleDto articleDto) {
        Article article = new Article(articleDto.getTitle(),
                articleDto.getContent(),
                articleDto.getCreateTime(),
                articleDto.getUpdateTime(),
                articleDto.getUserId(),
                "ANNOUNCEMENT");
        articleRepository.save(article);
        return null;
    }

    @Override
    public Article saveArticle(ArticleDto articleDto) {
        Article article = new Article(articleDto.getTitle(),
                articleDto.getContent(),
                articleDto.getCreateTime(),
                articleDto.getUpdateTime(),
                articleDto.getUserId());
        articleRepository.save(article);
        return null;
    }

    @Override
    public List<Article> getArticleByTitle(String title) {
        return (List<Article>) articleRepository.findByTitleRegex(title);
    }

    @Override
    public Optional<Article> getArticleById(String id) {
        return articleRepository.findById(id);
    }

    @Override
    public List<Article> getArticleByUserId(String userId) {
        return (List<Article>) articleRepository.findByUserId(userId);
    }

    @Override
    public void deleteById(String id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article saveEdit(ArticleDto articleDto, String id) {
        if (articleRepository.findById(id).isPresent()) {
            Article article = articleRepository.findById(id).get();

            article.setTitle(articleDto.getTitle());
            article.setContent(articleDto.getContent());
            article.setUpdateTime(new Date(System.currentTimeMillis()));

            article = articleRepository.save(article);
            return article;
        } else {
            return null;
        }
    }

    @Override
    public void setType(String id, String type) {
        if (articleRepository.findById(id).isPresent()) {
            Article article = articleRepository.findById(id).get();
            article.setType(type);
            articleRepository.save(article);
        }
    }

    @Override
    public List<Article> getArticleByType(String type) {
        return (List<Article>) articleRepository.findByType(type);
    }
}
