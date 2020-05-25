package com.example.cosmingtest;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecordActivity extends AppCompatActivity {

    private ArrayList<MainData> arrayList;
    private RecordAdapter mainadapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainadapter = new RecordAdapter(arrayList);
        recyclerView.setAdapter(mainadapter);

        MainData mainData;

        for(int i = 0; i < 10; i++){
            mainData = new MainData(R.mipmap.ic_launcher,"2020-02-02", "23:02:04","","","","","");
            arrayList.add(mainData);
        }

        mainadapter.notifyDataSetChanged();

        Toolbar toolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);//기본 제목을 없애줍니다.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.goback2);










   }
}
