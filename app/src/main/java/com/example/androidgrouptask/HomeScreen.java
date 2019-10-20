package com.example.androidgrouptask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeScreen extends AppCompatActivity {

    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }
}
