package com.example.cosmingtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ImageView imageView;
    byte[] byte_array;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        imageView = findViewById(R.id.rs_image);

        byte_array = getIntent().getByteArrayExtra("image");
        data = (ArrayList<String>)getIntent().getSerializableExtra("read_words");
        Bitmap bm = BitmapFactory.decodeByteArray(byte_array, 0, byte_array.length);
        imageView.setImageBitmap(bm);

        for(int i = 0; i < data.size(); i++){
            Log.i("array", data.get(i));
        }

    }
}
