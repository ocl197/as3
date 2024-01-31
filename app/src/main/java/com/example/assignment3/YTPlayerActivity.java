package com.example.assignment3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YTPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ytplayer);
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);

        String youtubeVideoId = getIntent().getStringExtra("youtubeVideoId");
        if(youtubeVideoId == null){
            youtubeVideoId = "G9zu_Ygswhk";
        }
        String finalYoutubeVideoId = youtubeVideoId;
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                // Set the video ID of the desired YouTube video

                youTubePlayer.loadVideo(finalYoutubeVideoId, 0);
            }


        });
        Button button = findViewById(R.id.mainMenuButton);
        button.setOnClickListener(view -> {
            Intent i = new Intent(YTPlayerActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        });





    }
}