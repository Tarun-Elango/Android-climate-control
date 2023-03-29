package com.example.a390cc;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//service class that fetches weather data of montreal
//and stores it into a shared preference.
public class weatherservice extends Service {
    public static final String shared_prefwet = "weather";
    public static final String tem = "temp";
    public static final String hum = "hum";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    //start thread
        threadwork t = new threadwork();

        t.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    //thread class that connects to open weather api, parses the json string and stores
    //it in a shared preference
  class threadwork extends Thread {
        String inputLine;


        @Override
        public void run() {
            super.run();

            String url = "http://api.openweathermap.org/data/2.5/weather?q=Montreal&APPID=17dd56b4114c5c2b4e4c33bcf0d60f1b";
            URL linkobj = null;
            try {
                linkobj = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection connectionHTTP = null;
            try {
                connectionHTTP = (HttpURLConnection) linkobj.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                connectionHTTP.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            connectionHTTP.setRequestProperty("User-Agent", "Mozilla/5.0");
            try {
                //comment here
                int responseCode = connectionHTTP.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader receivedvalue = null;
            try {
                receivedvalue = new BufferedReader(
                        new InputStreamReader(connectionHTTP.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            StringBuffer readValue = new StringBuffer();
            while (true) {
                try {
                    if (!((inputLine = receivedvalue.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                readValue.append(inputLine);
            }
            try {
                receivedvalue.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String output = readValue.toString();
            SharedPreferences sp = getSharedPreferences(shared_prefwet, MODE_PRIVATE);
           // Toast.makeText(weatherservice.this, "here", Toast.LENGTH_SHORT).show();
            weatherdatabase wd = new weatherdatabase(weatherservice.this);
            SharedPreferences.Editor edit  = sp.edit();
            //parse the string
            String temp = output.split("temp\":")[1].split(",")[0];
            String humv = output.split("humidity\":")[1].split(",")[0];
            edit.putString(tem,temp);
            edit.putString(hum, humv);
            edit.apply();
    //testing
//tabletwo t2 ;
//t2 = new tabletwo(-1, temp, humv);
//wd.addtwo(t2);

            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            }


    }
