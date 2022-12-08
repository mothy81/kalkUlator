package com.empro.kalk_u_lator;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

     private Button upButton, downButton, horizontalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_start);

        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);
        horizontalButton = findViewById(R.id.horizontalButton);

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, UpActivity.class);
                startActivity(intent);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(StartActivity.this, DownActivity.class);
                startActivity(intent2);
            }
        });

        horizontalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent3);
            }
        });




    }
}