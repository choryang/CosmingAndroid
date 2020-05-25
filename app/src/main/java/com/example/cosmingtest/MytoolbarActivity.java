package com.example.cosmingtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MytoolbarActivity extends AppCompatActivity {

    ImageView gohome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytoolbar);

        gohome = (ImageView)findViewById(R.id.gohome);

        gohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome_intent = new Intent(MytoolbarActivity.this, MainActivity.class);
                startActivity(gohome_intent);
            }
        });
    }
}