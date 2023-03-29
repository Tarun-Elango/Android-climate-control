package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

//this activity is the backend for the store analytics
public class shop_analytics extends AppCompatActivity {
protected TextView t35;
protected TextView t36;
protected FloatingActionButton button;
protected TextView t23;
    public static final String shared_prefwet = "weather";
    public static final String  tem = "temp";
    public static final String hum= "hum";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_analytics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    t35 = findViewById(R.id.textView35);
    t36 = findViewById(R.id.textView36);
    t23 =  findViewById(R.id.textView18);
    button = findViewById(R.id.floatingActionButton2);
    button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dialog();
    }
});



        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    //handler for setting the background text/suggestions
                  databasehelper db = new databasehelper(shop_analytics.this);
                  int temp = new Double(db.getlast()).intValue();
                   if(temp>15 ){
                        t35.setText("current temperature out of range");
                       t35.setTextColor(Color.RED);
                        int change = temp-15;
                        t36.setText("decrease indoor temperature by: "+ change +"°C");
                       t36.setTextColor(Color.BLUE);

                   }
                   else if(temp<10){
                       t35.setText("current temperature out of range");
                       t35.setTextColor(Color.RED);
                       int c2 = 10-temp;
                       t36.setText("increase indoor temperature by: "+ c2+ "°C");
                       t36.setTextColor(Color.RED);
                   }
                   else{
                       t35.setText("current temperature in range");
                       t35.setTextColor(Color.GREEN);
                       t36.setText("no changes required");
                       t35.setTextColor(Color.GREEN);
                   }


                   //getting the weather data and calculating the cop and
                    //displaying it on the screen
                    SharedPreferences sp = getSharedPreferences(shared_prefwet, MODE_PRIVATE);

                  int temperature = new Double(sp.getString(tem, "")).intValue();

                   int temkelv = temp + 273;

                  if(temperature>temkelv){

                    int  cop = temkelv/(temperature-temkelv);

                      t23.setText(String.valueOf(cop));
                  }
                  else{
                      int cop = temkelv/(temkelv-temperature);
                      t23.setText(String.valueOf(cop));
                  }

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

      //for the cop info dialog
    public void dialog(){
dialog d = new dialog();
d.show(getSupportFragmentManager(),"dialog");
    }



}