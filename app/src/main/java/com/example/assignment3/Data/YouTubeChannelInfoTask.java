package com.example.assignment3.Data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.assignment3.Model.Video;
import com.example.assignment3.Model.VideoManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class YouTubeChannelInfoTask extends AsyncTask<Void, Void, Void> {
    List<Video> videos = VideoManager.getInstance().getVideoList();
    private static final String API_KEY = "AIzaSyB8mLlFi2239J0Uc444OUS6bnu8Qg7LrAE";  // Replace with your actual API key

    private String channelUrl = "https://youtube.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics&id=UCaeru5t3v1M2rKyElMg8IQg&key=" + API_KEY;

    private String title;
    private String viewCount;
    private String videoCount;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL url = new URL(channelUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }

                bufferedReader.close();
                parseJson(stringBuilder.toString());

            } finally {
                urlConnection.disconnect();
            }

        } catch (IOException e) {
            Log.e("HTTPURLConnection", e.getMessage(), e);
        }
        return null;
    }

    private void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject channelInfo = jsonObject.getJSONArray("items").getJSONObject(0);

            // Extracting required information
            title = channelInfo.getJSONObject("snippet").getString("title");
            viewCount = channelInfo.getJSONObject("statistics").getString("viewCount");
            videoCount = channelInfo.getJSONObject("statistics").getString("videoCount");


        } catch (JSONException e) {
            Log.e("JSONParsing", e.getMessage(), e);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        // Now you can use 'title', 'viewCount', and 'videoCount' variables as needed
        Log.d("YouTube Channel Info", "Title: " + title);
        Log.d("YouTube Channel Info", "View Count: " + viewCount);
        Log.d("YouTube Channel Info", "Video Count: " + videoCount);



        saveToFirestore(title, viewCount, videoCount);
    }


    private void saveToFirestore(String title, String viewCount, String videoCount) {
        // Create a new document with the name "channelinfo"
        firestore.collection("channelinfo")
                .document("channelinfo")
                .set(new ChannelInfo(title, viewCount, videoCount))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("YourActivity", "Data saved to Firestore successfully!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("YourActivity", "Error saving data to Firestore: " + e.getMessage());
                    }
                });
    }
}