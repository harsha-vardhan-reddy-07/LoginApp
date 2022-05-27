package com.firstapp.loginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    // Intent: move to another activity an clicking a button
    // add Onclick = "Signup_page" to button in xml file
    public void SignUp_page(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}