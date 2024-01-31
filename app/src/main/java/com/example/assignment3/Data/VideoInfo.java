package com.example.assignment3.Data;

public class VideoInfo {
    private String title;
    private String description;
    private String thumbnailURL;
    private String videoId;

    public VideoInfo(String title, String description, String thumbnailURL, String videoId) {
        this.title = title;
        this.description = description;
        this.thumbnailURL = thumbnailURL;
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getVideoId() {
        return videoId;
    }
}
