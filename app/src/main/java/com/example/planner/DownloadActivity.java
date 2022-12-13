package com.example.planner;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        Intent receivedIntent = getIntent();
        String downloadRef = receivedIntent.getStringExtra("DOWNLOAD_REF");

        TextView textView = findViewById(R.id.download_ref);
        textView.setText(downloadRef);

        downloadImageTo(downloadRef);
    }

    private void downloadImageTo(String uri) {
        // Get a default Storage bucket
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a reference to a file from a Google Cloud Storage URI
        StorageReference gsReference = storage.getReferenceFromUrl(uri); // from gs://~~~

        // ImageView in your Activity
        ImageView imageView = findViewById(R.id.downloaded_imageview);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        Glide.with(this /* context */)
                .load(gsReference)
                .into(imageView);
    }
}