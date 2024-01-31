package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInClient mGoogleSignInClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Button signInButton = findViewById(R.id.googleAuthButton);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            Log.d("LoginActivity", "account is null");
        }


        signInButton.setOnClickListener(v -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 1);





        Button signOutButton = findViewById(R.id.button2);
        signOutButton.setOnClickListener(b -> {
            mGoogleSignInClient.signOut();


        });


    });
// Assume you have onActivityResult method in your activity
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            // The request code 1 corresponds to the Google Sign-In operation

            if (resultCode == RESULT_OK) {
                // Sign-in was successful
                // You can obtain user information from the signed-in account
                // and navigate to another activity
                Log.d("LoginActivity", "Sign-in successful");


                // Example: Navigate to another activity
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity if needed
            } else {
                Log.d("LoginActivity", "sign in failed");
                // Sign-in failed or was canceled by the user
                // You can display a prompt or take appropriate action

                // Example: Show a prompt
                Toast.makeText(this, "Sign-in failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
