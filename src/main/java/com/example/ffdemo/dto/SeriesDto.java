package com.example.ffdemo.dto;

import java.util.Collection;
import java.util.List;

public class SeriesDto {
    public String id;
    public String name;
    public String userId;
    public List<String> articleList;  // article id

    public SeriesDto(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }

    public SeriesDto(String name, String userId, List<String> articleList) {
        this.name = name;
        this.userId = userId;
        this.articleList = articleList;
    }

    public SeriesDto() {
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Collection<String> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<String> articleList) {
        this.articleList = articleList;
    }
}
