package com.example.materialtest.models;

public class Share {
    String content;
    String title;
    String UserId;
    String shopName;
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShopName() {
        return shopName;
    }

    public Share(String content, String title, String userId, String shopName) {
        this.content = content;
        this.title = title;
        this.UserId = userId;
        this.shopName = shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
