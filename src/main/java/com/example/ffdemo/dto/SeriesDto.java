package com.example.ffdemo.dto;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class SeriesDto {
    public String id;
    public String name;
    public String userId;
    public List<String> articleList;  // article id
    public Date createTime;
    public Date updateTime;

    public SeriesDto(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }

    public SeriesDto(String name, String userId, List<String> articleList) {
        this.name = name;
        this.userId = userId;
        this.articleList = articleList;
        this.createTime = new Date(System.currentTimeMillis());
        this.updateTime = new Date(System.currentTimeMillis());
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
        this.updateTime = new Date(System.currentTimeMillis());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        this.updateTime = new Date(System.currentTimeMillis());
    }

    public Collection<String> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<String> articleList) {
        this.articleList = articleList;
        this.updateTime = new Date(System.currentTimeMillis());
    }
}
