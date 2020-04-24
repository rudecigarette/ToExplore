package com.example.materialtest.models;

public class Store {
    private String StoreName;
    private String StoreInfo;
    private int ResourceId;
    private String StoreScore;
    public Store(String storeName, String storeInfo,int resourceId) {
        StoreName = storeName;
        StoreInfo = storeInfo;
        ResourceId = resourceId;
    }
    public Store(String storeName, String storeInfo,int resourceId,String storeScore) {
        StoreName = storeName;
        StoreInfo = storeInfo;
        ResourceId = resourceId;
        StoreScore = storeScore;
    }
    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getStoreInfo() {
        return StoreInfo;
    }

    public void setStoreInfo(String storeInfo) {
        StoreInfo = storeInfo;
    }

    public int getResourceId() {
        return ResourceId;
    }

    public void setResourceId(int resourceId) {
        ResourceId = resourceId;
    }

    public String getStoreScore() {
        return StoreScore;
    }

    public void setStoreScore(String storeScore) {
        StoreScore = storeScore;
    }
}
