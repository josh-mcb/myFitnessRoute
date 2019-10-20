package com.example.androidgrouptask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    EditText confirmPassword;
    TextView confirmPasswordLabel;
    Button noAccountRegisterBtn;
    Button registerButton;
    Button loginButton;
    Button cancelRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        confirmPassword = findViewById(R.id.confirmPasswordTB);
        confirmPasswordLabel = findViewById(R.id.passwordTitle2);
        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        noAccountRegisterBtn = findViewById(R.id.register_Now_Btn);
        cancelRegistration = findViewById(R.id.cancelRegisterButton);
        progressBar = findViewById(R.id.progressBar);

        confirmPassword.setVisibility(View.INVISIBLE); // hides the confirm password textbox
        confirmPasswordLabel.setVisibility(View.INVISIBLE); // hides the confirm password label
        registerButton.setVisibility(View.INVISIBLE);
        cancelRegistration.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();

    }
    // register button for showing the confirm password fields
    public void noAccountClick(View view) {
        confirmPassword.setVisibility(View.VISIBLE); // shows the confirm password textbox
        confirmPasswordLabel.setVisibility(View.VISIBLE); // shows the confirm password label
        registerButton.setVisibility(View.VISIBLE); // shows the register Button
        loginButton.setVisibility(View.INVISIBLE); // hide the login button
        noAccountRegisterBtn.setVisibility(View.INVISIBLE); // hide the register now button
        cancelRegistration.setVisibility(View.VISIBLE);
    }
    // takes user back to login screen if the registration button is accidently pressed.
    public void cancelRegistration(View view) {
        hideRegistration();
    }
    // button to add new users to Firebase database
    public void registerUsers(View view) {

        String email = ((EditText) findViewById(R.id.emailInputTB)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordInputTB)).getText().toString();
        String confirmPassword1 = ((EditText) findViewById(R.id.confirmPasswordTB)).getText().toString();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),  "Please Enter an Email!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter a Password!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(confirmPassword1)) {
            Toast.makeText(this, "Please Confirm Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.matches(confirmPassword1) == false) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return;
        }

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Registration Successful!", Toast.LENGTH_LONG).show();
                        hideRegistration();

                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        FirebaseAuthException e = (FirebaseAuthException)task.getException();
                        Toast.makeText(MainActivity.this, "Registration Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            });
        }

        public void hideRegistration() {
            confirmPassword.setVisibility(View.INVISIBLE);
            confirmPasswordLabel.setVisibility(View.INVISIBLE);
            registerButton.setVisibility(View.INVISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            noAccountRegisterBtn.setVisibility(View.VISIBLE);
            cancelRegistration.setVisibility(View.INVISIBLE);
        }

    public void loginUser(View view) {

        String email = ((EditText) findViewById(R.id.emailInputTB)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordInputTB)).getText().toString();

        if(TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),  "Please Enter an Email!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter a Password!", Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Login Unsuccessful, Email or Password is not recognised.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

