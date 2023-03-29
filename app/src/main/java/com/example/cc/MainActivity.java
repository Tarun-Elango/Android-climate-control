package com.example.a390cc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

//main page activity


public class MainActivity extends AppCompatActivity  {
    protected Button b_notify;
    protected Button b_past;
    protected Button b_control;
    protected Button b_update;
    protected Button restart;
    protected Button Stop;

    protected TextView text5;
    protected TextView text4;
    public static final String shared_pref = "analytics";
    public static final String  t = "temp";
    public static final String h= "hum";

    public static final String shared_p= "myfile";
    public static final String  idname = "id";

    private boolean check = true;
    final Handler handler = new Handler();
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restart= findViewById(R.id.Restart);
        ConstraintLayout constraintLayout= findViewById(R.id.mainlay);

         text5= findViewById(R.id.textView5);
        text4= findViewById(R.id.textView4);
        databasehelper db = new databasehelper(MainActivity.this);

        text5.setText(db.getlast());
        text4.setText(db.getlasthum());
          restart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
     //start the notification service
        startService(new Intent(MainActivity.this, notify_service.class));
    }
});
    b_update=findViewById(R.id.button_update);
       b_update.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               text5.setText(db.getlast());
               text4.setText(db.getlasthum());
           }

       });
        b_notify= findViewById(R.id.button_notify);
        b_past= findViewById(R.id.button_pastdata);
        b_control= findViewById(R.id.button3);
        b_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, notify.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, b_notify, ViewCompat.getTransitionName(b_notify));
                startActivity(intent, options.toBundle());

            }
        });
        b_past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, analytics_options.class);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, b_past, ViewCompat.getTransitionName(b_past));
                startActivity(intent, options.toBundle());
            }
        });

        b_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, control.class);

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, b_control, ViewCompat.getTransitionName(b_control));
                startActivity(intent, options.toBundle());
            }
        });

//start service for fetching data from the sensor database
        startService(new Intent(MainActivity.this, service.class));


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                   //handler for background
                    text5.setText(db.getlast());
                    text4.setText(db.getlasthum());
                    if (Integer.parseInt(db.getlast())<=23) {
                        constraintLayout.setBackground(getDrawable(R.drawable.background_cool));
                    }
                    else
                        constraintLayout.setBackground(getDrawable(R.drawable.background_warm));

                }
                catch (Exception e) {

                }
                finally{

                    handler.postDelayed(this, 1000);
                }
            }
        };


        handler.post(runnable);

        //start service for fetching weather data from open weather api
        startService(new Intent(MainActivity.this, weatherservice.class));

    }

//ui changes testing
    class uiupdate extends Thread{
        databasehelper db = new databasehelper(MainActivity.this);
        @Override
        public void run() {
            while(true){

            text5.setText(db.getlast());
            text4.setText(db.getlasthum());
        }}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menudot:
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }








}