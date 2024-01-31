package com.example.assignment3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.assignment3.Data.ListVideosTask;
import com.example.assignment3.Data.YouTubeChannelInfoTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;
    private DocumentReference channelInfoDocument;
    private ListenerRegistration listenerRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        super.onCreate(savedInstanceState);
        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();


        // Reference to the "channelinfo" document
        channelInfoDocument = firestore.collection("channelinfo").document("channelinfo");

        // Retrieve data from Firestore
        startListeningForUpdates();


        setContentView(R.layout.activity_main);
        new YouTubeChannelInfoTask().execute();
        new ListVideosTask().execute();
        Button playYT = findViewById(R.id.watchYT);
        playYT.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, YTPlayerActivity.class);
            startActivity(i);
        });
        Button listVideos = findViewById(R.id.button4);
        listVideos.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, ListVideosActivity.class);
            startActivity(i);
        });

    }
    private void startListeningForUpdates() {
        listenerRegistration = channelInfoDocument.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e("YourActivity", "Listen failed: " + e.getMessage());
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    // Document found, extract updated data
                    String title = documentSnapshot.getString("title");
                    String viewCount = documentSnapshot.getString("viewCount");
                    String videoCount = documentSnapshot.getString("videoCount");

                    // Now you can use the updated data as needed
                    Log.d("YourActivity", "Title: " + title);
                    Log.d("YourActivity", "View Count: " + viewCount);
                    Log.d("YourActivity", "Video Count: " + videoCount);
                } else {
                    Log.d("YourActivity", "Current data: null");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Stop listening for updates when the activity is destroyed
        if (listenerRegistration != null) {
            listenerRegistration.remove();
        }
    }

}