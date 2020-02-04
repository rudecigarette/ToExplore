package com.example.materialtest.models;

public class StoreInfo {
    private int id;
    private String shopname;
    private float star;
    private String typename;
    private String Ename;
    private String comment;
    private int click;

    public StoreInfo(int id, String shopname, float star, String typename, String ename, String comment, int click) {
        this.id = id;
        this.shopname = shopname;
        this.star = star;
        this.typename = typename;
        Ename = ename;
        this.comment = comment;
        this.click = click;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getEname() {
        return Ename;
    }

    public void setEname(String ename) {
        Ename = ename;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
