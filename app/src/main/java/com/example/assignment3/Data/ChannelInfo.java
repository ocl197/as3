package com.example.assignment3.Data;

public class ChannelInfo {
    private String title;
    private String viewCount;
    private String videoCount;

    public ChannelInfo(String title, String viewCount, String videoCount) {
        this.title = title;
        this.viewCount = viewCount;
        this.videoCount = videoCount;
    }
    public ChannelInfo(){

    }

    public String getTitle() {
        return title;
    }

    public String getViewCount() {
        return viewCount;
    }

    public String getVideoCount() {
        return videoCount;
    }
}
