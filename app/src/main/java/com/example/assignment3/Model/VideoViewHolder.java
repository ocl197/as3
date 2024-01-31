package com.example.assignment3.Model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment3.R;

public class VideoViewHolder extends RecyclerView.ViewHolder {
    public ImageView videoImageView;
    public TextView titleTextView;
    public TextView descriptionTextView;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoImageView = itemView.findViewById(R.id.videoImageView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
    }
}
