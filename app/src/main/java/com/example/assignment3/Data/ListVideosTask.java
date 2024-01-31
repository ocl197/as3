package com.example.assignment3.Data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListVideosTask extends AsyncTask<Void, Void, Void> {
    // Replace with your actual API key

    private String channelUrl = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyB8mLlFi2239J0Uc444OUS6bnu8Qg7LrAE&channelId=UCaeru5t3v1M2rKyElMg8IQg&%E2%88%82=snippet,id&order=date&maxResults=20";

    private String title;
    private String viewCount;
    private String videoCount;
    Map<String, Object> data = new HashMap<>();
    List<String> videoIds = new ArrayList<>();
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
                Log.d("VideoID", stringBuilder.toString());

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
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Extracting required information
            for (int i = 0; i < itemsArray.length(); i++) {
                // Get the current item
                JSONObject currentItem = itemsArray.getJSONObject(i);

                // Get the video ID from the "id" object within the current item
                JSONObject idObject = currentItem.getJSONObject("id");
                String videoId = idObject.optString("videoId");

                // Add the video ID to the list
                videoIds.add(videoId);
            }


        } catch (JSONException e) {
            Log.e("JSONParsing", e.getMessage(), e);
        }
    }

    @Override
    protected void onPostExecute(Void result) {
        // Now you can use 'title', 'viewCount', and 'videoCount' variables as needed
        for(String videoId : videoIds)
        {
            String s = "ID" + videoId;

            Log.d("VideoID", s);
        }
        data.put("videoIds", videoIds);
        saveToFirestore(data);


    }
    private void saveToFirestore(Map<String, Object> data) {
        // Create a new document with the name "channelinfo"
        firestore.collection("videos")
                .document("videos")
                .set(data)
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