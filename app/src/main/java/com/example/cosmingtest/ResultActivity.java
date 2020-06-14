package com.example.cosmingtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ImageView imageView;
    ImageView setLike;
    byte[] byte_array;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        setLike = findViewById(R.id.set_like);

        setLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent like_pop = new Intent(ResultActivity.this, LikepopActivity.class);
                startActivity(like_pop);
            }
        });

        /*imageView = findViewById(R.id.rs_image);

        data = (ArrayList<String>)getIntent().getSerializableExtra("read_words");
        byte_array = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte_array, 0, byte_array.length);
        imageView.setImageBitmap(bitmap);

        for(int i = 0; i < data.size(); i++) {
            Log.i("array", data.get(i));
        }*/

    }
}
