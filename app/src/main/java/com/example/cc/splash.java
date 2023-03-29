package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

//splash on startup
public class splash extends AppCompatActivity {
    private final int splashDuration = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Direct splash go the Home page
                Intent intent = new Intent(splash.this, login.class);
                startActivity(intent);
                splash.this.finish();
            }
        }, splashDuration);
    }
}