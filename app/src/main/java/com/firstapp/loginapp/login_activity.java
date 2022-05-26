package com.firstapp.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_activity extends AppCompatActivity {

    //Views
    EditText mEmailEt, mPasswordEt;
    Button mLoginBtn;

    //Firebase Auth
    private FirebaseAuth mAuth;

    //Progress bar
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        //init
        mEmailEt = findViewById(R.id.emailField1);
        mPasswordEt = findViewById(R.id.passwordField1);
        mLoginBtn = findViewById(R.id.button);

        //Login Button Click
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Input data
                String email = mEmailEt.getText().toString();
                String password = mPasswordEt.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //Display error and focus to email field
                    mEmailEt.setError("Invaild Email");
                    mEmailEt.setFocusable(true);
                }
                else {
                    loginUser(email, password);
                }

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

    }

    private void loginUser(String email, String password) {

        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, dismiss progressbar
                            progressDialog.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();

                            startActivity(new Intent(login_activity.this, HomePage.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();

                            Toast.makeText(login_activity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, get and show error message
                progressDialog.dismiss();
                Toast.makeText(login_activity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }





    public void SignUp_page(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void Google_btn(View view) {
        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
        startActivity(intent1);
    }
}