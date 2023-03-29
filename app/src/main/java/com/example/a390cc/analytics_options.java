package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
//this class in the class that gives user three options after pressing the analytics button

public class analytics_options extends AppCompatActivity {
protected Button pastdata;
    protected Button shop;
    protected Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics_options);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pastdata =findViewById(R.id.buttonPastData);
        ConstraintLayout constraintLayout= findViewById(R.id.lay);
        pastdata.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(analytics_options.this, analytics.class);
        startActivity(intent);
    }
});

shop = findViewById(R.id.buttonShop);
shop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(analytics_options.this, shop_analytics.class);
        startActivity(intent);
    }
});

home = findViewById(R.id.buttonHome);
home.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(analytics_options.this, home_analytics.class);
        startActivity(intent);
    }
});

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    //handler class to change background view
                    databasehelper db = new databasehelper(analytics_options.this);
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
    }

}