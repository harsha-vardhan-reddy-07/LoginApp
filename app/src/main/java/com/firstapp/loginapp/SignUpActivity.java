package com.firstapp.loginapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignUpActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;


    //Views
    EditText mEmailEt, mPasswordEt, mConPasswordEt;
    Button mSignupBtn;
    ImageButton mGoogleBtn;
    GoogleSignInClient mGoogleSigninClient;

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
        mGoogleBtn = findViewById(R.id.google_btn);

        //Before mAuth
        //Configure Google signin

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();

        mGoogleSigninClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User.....");

        //Handle signup button
        mSignupBtn.setOnClickListener(view -> {
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
        });

        //Google Button sign in
        mGoogleBtn.setOnClickListener(view -> {
            //Begin google login process
            Intent signinIntent = mGoogleSigninClient.getSignInIntent();
            startActivityForResult(signinIntent, RC_SIGN_IN);

        });





    }

    private void registerUser(String email, String password) {
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
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
                }).addOnFailureListener(e -> {
                    //error, get and show error message
                    progressDialog.dismiss();
                    Toast.makeText(SignUpActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }


    //Continue with google button

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // ...
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        //Show mail in toast
                        Toast.makeText(SignUpActivity.this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();

                        //Go to home activity
                        startActivity(new Intent(SignUpActivity.this, HomePage.class));
                        finish();

                    } else {
                        Toast.makeText(SignUpActivity.this, "Login Failed...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    //error, get and show error message

                    Toast.makeText(SignUpActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



    // Signup to Login Page Link

    public void Login_Page(View view) {

        Intent intent2 = new Intent(this, login_activity.class);
        startActivity(intent2);
        finish();
    }


}