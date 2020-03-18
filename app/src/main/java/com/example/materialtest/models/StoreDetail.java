package com.example.materialtest.models;

public class StoreDetail {
    private String storePhone;
    private String openTime;

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }
    public StoreDetail(String storePhone,String openTime){
        this.openTime = openTime;
        this.storePhone = storePhone;
    }
}
