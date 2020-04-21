package com.example.materialtest.models;

public class Store {
    private String StoreName;
    private String StoreInfo;
    private int ResourceId;
    public Store(String storeName, String storeInfo,int resourceId) {
        StoreName = storeName;
        StoreInfo = storeInfo;
        ResourceId = resourceId;
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
}
