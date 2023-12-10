package com.example.myapprute;

public class UserDetail {
    private String title;
    private String detail;
    private int imageResourceId; // ID-ul resursei de imagine

    public UserDetail(String title, String detail, int imageResourceId) {
        this.title = title;
        this.detail = detail;
        this.imageResourceId = imageResourceId;
    }

    // Getteri și Setteri
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}
