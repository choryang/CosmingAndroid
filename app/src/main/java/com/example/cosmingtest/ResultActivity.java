package com.example.cosmingtest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    ImageView imageView;
    ImageView setLike;

    private ArrayList<ResultData> dataList;
    private ResultAdapter resultAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

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

        recyclerView = findViewById(R.id.rv_result);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        dataList = new ArrayList<>();

        resultAdapter = new ResultAdapter(dataList);
        recyclerView.setAdapter(resultAdapter);

        ResultData resultData;

        int ewg = R.drawable.ewg_4;
        String ing_data = "Robust";

        for(int i = 0; i < 10; i++){
            resultData = new ResultData("세테아릴알코올", ewg,ing_data,"배합목적 배합목적 배합목적");
            dataList.add(resultData);
        }

        resultAdapter.notifyDataSetChanged();

        imageView = findViewById(R.id.rs_image);

        ArrayList<String> read_data = (ArrayList<String>)getIntent().getSerializableExtra("read_words");
        byte[] byte_array = getIntent().getByteArrayExtra("image");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byte_array, 0, byte_array.length);
        imageView.setImageBitmap(bitmap);

        for(int i = 0; i < read_data.size(); i++) {
            Log.i("array", read_data.get(i));
        }
    }
}
