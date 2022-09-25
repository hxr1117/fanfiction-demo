package com.example.ffdemo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Collection;

@Document
public class Series {
    @Id
    private String id;
    private String name;
    private String userId;
    private Collection<String> articleList;  // article_id

    public Series(String name, String userId) {
        this.userId = userId;
        this.name = name;
    }

    public Series(String name, String userId, Collection<String> articleList) {
        this.name = name;
        this.userId = userId;
        this.articleList = articleList;
    }

    public Series(String id, String name, String userId, Collection<String> articleList) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.articleList = articleList;
    }

    public Series() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getArticleList() {
        return articleList;
    }

    public void setArticleList(Collection<String> articleList) {
        this.articleList = articleList;
    }

    public void addNewArticle(Article article) {
        this.articleList.add(article.getId());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean containArticle(String articleId) {
        return this.articleList.contains(articleId);
    }

    @Override
    public String toString() {
        StringBuilder ar = new StringBuilder();
        for (String article : articleList) {
            ar.append(article).append(" ");
        }
        return this.getUserId() + this.getName() + ar;
    }
}
