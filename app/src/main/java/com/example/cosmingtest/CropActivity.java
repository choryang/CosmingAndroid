package com.example.cosmingtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class CropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        ImageView imageView = (ImageView)findViewById(R.id.cr_image);

        byte[] arr = getIntent().getByteArrayExtra("image");
        Bitmap bm = BitmapFactory.decodeByteArray(arr, 0, arr.length);

        imageView.setImageBitmap(bm);
    }
}
