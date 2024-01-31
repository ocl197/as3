package com.example.assignment3.Model;

import java.util.ArrayList;
import java.util.List;

public class VideoManager {
    private static VideoManager instance;
    private List<Video> videoList;

    private VideoManager() {
        videoList = new ArrayList<>();
        // Initialize or load videos if needed
    }

    public static synchronized VideoManager getInstance() {
        if (instance == null) {
            instance = new VideoManager();
        }
        return instance;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videos) {
        videoList = videos;
    }

    public void addVideo(Video video) {
        videoList.add(video);
    }

    // Add other methods as needed
}
