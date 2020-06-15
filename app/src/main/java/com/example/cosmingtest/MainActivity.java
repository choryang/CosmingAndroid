package com.example.cosmingtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView search;
    ImageView record;
    ImageView like;
    ImageView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        search = (ImageView) findViewById(R.id.search);
        record = (ImageView)findViewById(R.id.record);
        like = (ImageView)findViewById(R.id.like);
        info = (ImageView)findViewById(R.id.info);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent search_intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(search_intent);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent record_intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(record_intent);
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent like_intent = new Intent(MainActivity.this, LikeActivity.class);
                startActivity(like_intent);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info_intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(info_intent);
            }
        });

    }
}
