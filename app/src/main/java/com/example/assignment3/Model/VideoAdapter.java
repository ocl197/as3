package com.example.assignment3.Model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignment3.R;
import com.example.assignment3.YTPlayerActivity;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private List<Video> videos;

    public VideoAdapter(List<Video> videos) {
        this.videos = videos;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {


        Video video = videos.get(position);
        holder.itemView.setOnClickListener(view -> {
            String videoId = video.getVideoID();
            Intent i = new Intent(holder.itemView.getContext(), YTPlayerActivity.class);
            i.putExtra("youtubeVideoId", videoId);
            holder.itemView.getContext().startActivity(i);
                });

        // Set data to views
        Glide.with(holder.itemView.getContext())
                .load(video.getImageUrl())
                .into(holder.videoImageView);

        holder.titleTextView.setText(video.getTitle());
        holder.descriptionTextView.setText(video.getDescription());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
