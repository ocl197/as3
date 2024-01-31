package com.example.assignment3.Model;

public class Video {
    private String titleVideo;
    private String description;
    private String imageUrl;
    private String videoID;

    public Video(String titleVideo, String description, String imageUrl, String videoID) {
        this.titleVideo = titleVideo;
        this.description = description;
        this.imageUrl = imageUrl;
        this.videoID = videoID;

    }

    public String getTitle() {
        return titleVideo;
    }
    public String getVideoID(){
        return videoID;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
