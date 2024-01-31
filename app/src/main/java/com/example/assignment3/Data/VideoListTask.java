package com.example.assignment3.Data;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.List;

public class VideoListTask{
    public static Task<List<String>> getVideoList() {
        TaskCompletionSource<List<String>> taskCompletionSource = new TaskCompletionSource<>();

        // Assuming you have a Firestore instance and a collection reference
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference channelVideoCollection = db.collection("your_collection_name");
        DocumentReference channelVideoListDocument = channelVideoCollection.document("your_document_id");

        channelVideoListDocument.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    // Assuming that the Firestore document contains a field named "videoIds"
                    List<String> videos = Collections.singletonList(document.get("videoIds", String.class));

                    if (videos != null) {
                        // Now 'videos' list contains the values from the Firestore document
                        taskCompletionSource.setResult(videos);
                    } else {
                        // Handle the case where 'videos' field is null in the Firestore document
                        taskCompletionSource.setException(new NullPointerException("Video list is null"));
                    }
                } else {
                    // Handle the case where the document doesn't exist
                    taskCompletionSource.setException(new Exception("Document does not exist"));
                }
            } else {
                // Handle the case where getting the document was not successful
                Exception exception = task.getException();
                if (exception != null) {
                    exception.printStackTrace();
                    taskCompletionSource.setException(exception);
                }
            }
        });

        return taskCompletionSource.getTask();
    }
}
