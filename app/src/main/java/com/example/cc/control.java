package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//this class is the last feature,
//has three buttons
//connects to firebase
//and sends respective messages

public class control extends AppCompatActivity {
protected Button Three;
protected Button OFF;
protected Button One;
protected Button Two;

    DatabaseReference databaseReference;
    LEDButtons ledButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ConstraintLayout constraintLayout= findViewById(R.id.back);
        One = findViewById(R.id.button4);
        Two = findViewById(R.id.button6);
        ledButtons = new LEDButtons();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("LEDButtons");
        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledButtons.setLed("Red");
                databaseReference.push().setValue(ledButtons);
                Toast.makeText(control.this,"Air Conditioner turned ON", Toast.LENGTH_SHORT).show();
            }
        });
        One.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledButtons.setLed("Red");
                databaseReference.setValue(ledButtons);
                Toast.makeText(control.this,"Air Conditioner is turned ON", Toast.LENGTH_SHORT).show();
            }
        });

        Two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ledButtons.setLed("Blue");
                databaseReference.setValue(ledButtons);
                Toast.makeText(control.this,"Humidifier is turned ON", Toast.LENGTH_SHORT).show();
            }
        });

Three = findViewById(R.id.button);
Three.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ledButtons.setLed("Green");
        databaseReference.setValue(ledButtons);
        Toast.makeText(control.this,"Purifier is turned ON", Toast.LENGTH_SHORT).show();
    }
});

OFF = findViewById(R.id.button2);
OFF.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        databaseReference.removeValue();
        Toast.makeText(control.this,"All devices are turned OFF", Toast.LENGTH_SHORT).show();
    }
});


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    //handler to set the background
                    AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
                    animationDrawable.setEnterFadeDuration(2500);
                    animationDrawable.setExitFadeDuration(5000);
                    animationDrawable.start();


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