package com.example.ffdemo.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class ArticleDto {
    public String title;
    public String content;
    public String userId;
    private Date createTime;
    private Date updateTime;
    private String type;

    public ArticleDto(String title, String content, String userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public ArticleDto(String title, String content, String userId, Date createTime, Date updateTime) {
        this.title = title;
        this.content = content;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArticleDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
