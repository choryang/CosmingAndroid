package com.example.cosmingtest;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ImageView imageView;
    byte[] byte_array;
    String uri;
    Uri image_uri;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        imageView = findViewById(R.id.rs_image);

        uri = getIntent().getStringExtra("image");
        image_uri = Uri.parse(uri);
        data = (ArrayList<String>)getIntent().getSerializableExtra("read_words");
        imageView.setImageURI(image_uri);

        for(int i = 0; i < data.size(); i++) {
            Log.i("array", data.get(i));
        }

    }
}
