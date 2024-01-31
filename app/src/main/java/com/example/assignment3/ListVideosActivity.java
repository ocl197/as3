package com.example.assignment3;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.assignment3.Data.ChannelInfo;
import com.example.assignment3.Data.FetchVideoInfoTask;
import com.example.assignment3.Model.StringListHolder;
import com.example.assignment3.Model.Video;
import com.example.assignment3.Model.VideoAdapter;
import com.example.assignment3.Model.VideoManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListVideosActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private DocumentReference channelInfoDocument;
    private DocumentReference channelVideoListDocument;
    private ListenerRegistration listenerRegistration;
    private DocumentSnapshot documentSnapshot;
    FetchVideoInfoTask fetchVideoInfoTask = new FetchVideoInfoTask();
    private List<String> videoIdList = new ArrayList<>();
    VideoManager videoManager = VideoManager.getInstance();
    List<Video> getVideos = videoManager.getVideoList();

    List<String> myStrings = StringListHolder.getInstance().getStringList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();
        channelInfoDocument = firestore.collection("channelinfo").document("channelinfo");
        channelVideoListDocument = firestore.collection("videos").document("videos");



        super.onCreate(savedInstanceState);


        Log.d("VideoCount", String.valueOf(getCountVideos()));
        setContentView(R.layout.activity_list_videos);
        listenerRegistration = channelInfoDocument.addSnapshotListener((documentSnapshot, e) -> {
            if (e != null) {
                // Handle the error
                Log.e("ListVideosActivity", "Error getting channel info", e);
                return;
            }

            if (documentSnapshot != null && documentSnapshot.exists()) {
                // DocumentSnapshot is ready, update UI
                ChannelInfo channelInfo = documentSnapshot.toObject(ChannelInfo.class);
                updateChannelInfoUI(channelInfo);
            } else {
                // Handle the case where the document doesn't exist
            }
        });
        videoList(channelVideoListDocument);














    }
    private void updateChannelInfoUI(ChannelInfo channelInfo) {
        TextView channelName = findViewById(R.id.channelInfo);
        channelName.setText("Channel Name: " + channelInfo.getTitle());

        TextView viewCountTextView = findViewById(R.id.viewCount);
        viewCountTextView.setText("View Count: " + channelInfo.getViewCount());

        TextView videoCountTextView = findViewById(R.id.videoCount);
        videoCountTextView.setText("Video Count: " + channelInfo.getVideoCount());

        Log.d("VideoCount", String.valueOf(getCountVideos()));
    }
    private void videoList(DocumentReference channelVideoListDocument) {
        channelVideoListDocument.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    List<String> videos = (List<String>) document.get("videoIds");

                    if (videos != null) {
                        myStrings.addAll(videos);
                        fetchVideoInfoAndUpdateUI();
                    } else {
                        // Handle the case where 'videos' field is null in the Firestore document
                    }
                } else {
                    // Handle the case where the document doesn't exist
                }
            } else {
                // Handle the case where getting the document was not successful
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                }
            }
        });
    }

    public void fetchVideoInfoAndUpdateUI() {
        new FetchVideoInfoTask() {
            @Override
            protected void onPostExecute(Void result) {
                VideoManager videoManager = VideoManager.getInstance();
                List<Video> getVideos = videoManager.getVideoList();


                // Update the UI with the new data
                RecyclerView recyclerView = findViewById(R.id.recylerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListVideosActivity.this));
                recyclerView.setAdapter(new VideoAdapter(getVideos));
                Button button = findViewById(R.id.mainMenuButton);
                button.setOnClickListener(v -> {

                    Intent i = new Intent(ListVideosActivity.this, MainActivity.class);
                    startActivity(i);
                    // Execute the AsyncTask

                });
            }
        }.execute();
    }


 public int getCountVideos(){

        List<Video> getVideos = videoManager.getVideoList();
        return getVideos.size();
 }


}


// Fetch the document
