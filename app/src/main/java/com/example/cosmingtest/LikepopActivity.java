package com.example.cosmingtest;

import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class LikepopActivity extends AppCompatActivity {

    Spinner spinner_year;
    Spinner spinner_month;
    Spinner spinner_day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);//화면 전환 애니메이션 삭제
        setContentView(R.layout.activity_likepop);
        final String[] year = getResources().getStringArray(R.array.year);
        final String[] month = getResources().getStringArray(R.array.month);
        final String[] day = getResources().getStringArray(R.array.day);
        spinner_year = findViewById(R.id.spinner_year);
        spinner_month = findViewById(R.id.spinner_month);
        spinner_day = findViewById(R.id.spinner_day);
        SpinnerAdapter adapter_year = new SpinnerAdapter(year, this);
        SpinnerAdapter adapter_month = new SpinnerAdapter(month, this);
        SpinnerAdapter adapter_day = new SpinnerAdapter(day, this);
        spinner_year.setAdapter(adapter_year);
        spinner_month.setAdapter(adapter_month);
        spinner_day.setAdapter(adapter_day);
    }

    @Override
    public void onBackPressed() { // 뒤로가기 버튼 눌렀을 때
        super.onBackPressed();
        overridePendingTransition(0, 0);//화면 전환 애니메이션 삭제
    }
}
