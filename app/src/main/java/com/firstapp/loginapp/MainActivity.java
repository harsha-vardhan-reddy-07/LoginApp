package com.firstapp.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {

    public static int SPLASH_SCREEN = 4000;

    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, HomePage.class));
                finish();
            }
        }, 4000);



    }
}