package com.example.materialtest.db;

import org.litepal.crud.LitePalSupport;

public class User extends LitePalSupport {
    private String phone;
    private String passward;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }
}
