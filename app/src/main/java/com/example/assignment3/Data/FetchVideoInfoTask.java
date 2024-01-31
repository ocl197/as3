package com.example.assignment3.Data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.assignment3.Model.StringListHolder;
import com.example.assignment3.Model.Video;
import com.example.assignment3.Model.VideoManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class FetchVideoInfoTask extends AsyncTask<Void, Void, Void> {
    VideoManager videoManager = VideoManager.getInstance();
    StringListHolder stringListHolder = StringListHolder.getInstance();
    List<String> videoIds = stringListHolder.getStringList();



    @Override
    protected Void doInBackground(Void... voids) {

        videoManager.getVideoList().clear();
        for (String videoId : videoIds) {
            if (videoId != null && videoId != "" && videoId != " " && !videoId.isEmpty()) {
                try {
                    final String API_KEY = "AIzaSyB8mLlFi2239J0Uc444OUS6bnu8Qg7LrAE";  // Replace with your actual API key

                    String apiUrl = "https://youtube.googleapis.com/youtube/v3/videos?part=snippet%2CcontentDetails%2Cstatistics&id=" + videoId + "&key=AIzaSyAvp0vSVdKE3Qa6bN8xMP165K7n6SA5uq8";
                    Log.d("apiUrl", apiUrl);

                    // Make an HTTP request
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    // Read the response
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Parse the JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());


                    JSONArray items = jsonResponse.getJSONArray("items");
                    JSONObject video = items.getJSONObject(0);
                    JSONObject snippet = video.getJSONObject("snippet");


                    // Get title, description, and image URL
                    String title = snippet.getString("title");

                    String description = snippet.getString("description");

                    JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                    JSONObject defaultThumbnail = thumbnails.getJSONObject("high");
                    String imageUrl = defaultThumbnail.getString("url");


                    // Print or use the values as needed

                    Video video1 = new Video(title, description, imageUrl, videoId);

                    videoManager.addVideo(video1);

                } catch (Exception e) {
                    Log.e("error", "Error occurred", e);
                }
            }


            // You can override onPostExecute to perform any UI updates after the background task finishes
            // For example, update UI elements with the obtained data


        }
        ;
        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
        // Update UI if needed
    }
}
