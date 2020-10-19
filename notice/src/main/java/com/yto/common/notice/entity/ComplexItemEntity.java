package com.yto.common.notice.entity;

/**
 * Created by GongWen on 16/12/27.
 */

public class ComplexItemEntity {

    private String name;
    private String apiUrl;
    private String time;

    public ComplexItemEntity(String name, String apiUrl) {
        this.name = name;
        this.apiUrl = apiUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
