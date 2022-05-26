package com.firstapp.loginapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    //Firebase Auth
    FirebaseAuth firebaseAuth;

    TextView mEmailTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mEmailTv = findViewById(R.id.show_mail);

        //init
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void checkUserStatus(){

        //get user status
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            //user is signed in and user stay here!!
            mEmailTv.setText(user.getEmail());

        }
        else{
            //User not signed in, go to login page
            startActivity(new Intent(HomePage.this, login_activity.class));
            finish();
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart(){
        //check on start of app
        checkUserStatus();
        super.onStart();
    }

    //Logout

    public void Logout(View view) {
        firebaseAuth.signOut();
        checkUserStatus();
    }
}