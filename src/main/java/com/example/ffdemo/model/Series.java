package com.example.ffdemo.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Collection;
import java.util.Date;

@Document
public class Series {
    @Id
    private String id;
    private String name;
    private String userId;
    private Collection<String> articleList;  // article_id
    private Date updateTime;
    private Date createTime;

    public Series(String name, String userId) {
        this.userId = userId;
        this.name = name;
    }

    public Series(String name, String userId, Collection<String> articleList) {
        this.name = name;
        this.userId = userId;
        this.articleList = articleList;
        this.createTime = new Date(System.currentTimeMillis());
        this.updateTime = new Date(System.currentTimeMillis());
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
        this.updateTime = new Date(System.currentTimeMillis());
    }

    public Collection<String> getArticleList() {
        return articleList;
    }

    public void setArticleList(Collection<String> articleList) {
        this.articleList = articleList;
        this.updateTime = new Date(System.currentTimeMillis());
    }

    public void addNewArticle(Article article) {
        this.articleList.add(article.getId());
        this.updateTime = new Date(System.currentTimeMillis());
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        this.updateTime = new Date(System.currentTimeMillis());
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
