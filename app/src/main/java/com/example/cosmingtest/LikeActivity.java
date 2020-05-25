package com.example.cosmingtest;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LikeActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        recyclerView = (RecyclerView)findViewById(R.id.rv2);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        LikeAdapter mainAdapter = new LikeAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        MainData mainData;

        for(int i = 0; i < 10; i++){
            mainData = new MainData(R.mipmap.ic_launcher,"","","핑크 어쩌구 세럼","제품 유형","2020-02-24","2021-02-24","우와아아아아아아아아");
            arrayList.add(mainData);
        }

        mainAdapter.notifyDataSetChanged();

        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.goback2);




    }
}
