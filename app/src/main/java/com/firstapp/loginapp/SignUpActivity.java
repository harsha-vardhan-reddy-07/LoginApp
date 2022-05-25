package com.firstapp.loginapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    //Setup custom actionbar
    private Toolbar toolbar;

    //Views
    EditText mNameEt, mEmailEt, mPasswordEt;
    Button mSignupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //init



    }

    //Continue with google button

    public void Google_btn(View view) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(intent1);
    }

    // Signup to Login Page Link

    public void Login_Page(View view) {

        Intent intent2 = new Intent(this, login_activity.class);
        startActivity(intent2);
    }


}