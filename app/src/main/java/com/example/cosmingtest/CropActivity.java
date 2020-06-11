package com.example.cosmingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CropActivity extends AppCompatActivity {

    ImageView imageView;
    Button re_search;
    Button go_result;
    SearchActivity sa = (SearchActivity)SearchActivity.search_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        sa.finish();

        imageView = findViewById(R.id.cr_image);
        re_search = findViewById(R.id.re_search);
        go_result = findViewById(R.id.go_result);

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap bm = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        imageView.setImageBitmap(bm);

        re_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_intent = new Intent(CropActivity.this, SearchActivity.class);
                startActivity(search_intent);
            }
        });

        go_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent result_intent = new Intent(CropActivity.this, ResultActivity.class);
                startActivity(result_intent);
            }
        });
    }

}
