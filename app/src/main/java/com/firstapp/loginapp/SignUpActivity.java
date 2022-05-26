package com.firstapp.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    //Setup custom actionbar
    private Toolbar toolbar;

    //Views
    EditText mEmailEt, mPasswordEt, mConPasswordEt;
    Button mSignupBtn;

    // Add progress of registration
    ProgressDialog progressDialog;

    //Firebase auth token
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //init
        mEmailEt = findViewById(R.id.emailField1);
        mPasswordEt = findViewById(R.id.passwordField1);
        mConPasswordEt = findViewById(R.id.confirmPasswordField);
        mSignupBtn = findViewById(R.id.Signup_button);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User.....");

        //Handle signup button
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //input name, Email, Password

                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();
                String conPassword = mConPasswordEt.getText().toString().trim();

                // Validate email
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //Set error and focus to Email
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                }

                //Validate -> 1 (Password must be >= 8 char long)
                else if(password.length() < 8){
                    //set Error and focus to Password
                    mPasswordEt.setError("Password should at least be 8 characteristics");
                    mPasswordEt.setFocusable(true);
                }

                //Validate -> 1 (check password and confirm password)
                else if(!password.equals(conPassword)){
                    //set Error and focus to Confirm Password
                    mConPasswordEt.setError("Enter same password");
                    mConPasswordEt.setFocusable(true);
                }

                else{
                    registerUser(email, password);
                }
            }
        });





    }

    private void registerUser(String email, String password) {
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //If signin successful, then dismiss the progressbar
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(SignUpActivity.this, "Registered...\n"+user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, HomePage.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, get and show error message
                progressDialog.dismiss();
                Toast.makeText(SignUpActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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