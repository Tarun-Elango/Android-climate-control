package com.example.a390cc;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

//this is the service class that connects to database's json url
//and fetches data
public class service extends Service {

    public static final String shared_p= "myfile";
    public static final String  idname = "id";
    public static final String shared_pref = "storetemp";
    public static final String  n = "up";
    public static final String p= "down";
    public static final String shared_pref2 = "storehum";
    public static final String  n2 = "hup";
    public static final String p2= "hdown";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//start thread
        threadwork t= new threadwork();

            t.start();

      return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //stop service
        super.onDestroy();
       threadwork t= new threadwork();
        try {
            t.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //thread class that connects to the database's json url
    class threadwork extends Thread{
        String inputLine;

        @Override
        public void run() {
            super.run();

            while (true) {
                SharedPreferences.Editor editor = getSharedPreferences(shared_p, MODE_PRIVATE).edit();

                SharedPreferences prefs = getSharedPreferences(shared_p, MODE_PRIVATE);
                int check = prefs.getInt(idname, 0);


                if (check == 0) {


                    editor.putInt(idname, 1);
                    editor.apply();
                }
                String url = "https://opensheet.vercel.app/16IyDyTCPeNuPWu44DPaqkSylx6csESJcsI3g4mMmEac/sheet1";
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


                //the following are the conditions for reading the json string,
                //to prevent re-reading already stored values, various parameters are used
                SharedPreferences sp = getSharedPreferences(shared_p, MODE_PRIVATE);
                int startvalue = sp.getInt(idname, -1);
                if (startvalue != output.length()) {
                    String strFind = "Date";
                    int count = 0, fromIndex = startvalue;
                    while ((fromIndex = output.indexOf(strFind, fromIndex)) != -1) {
                        count++;
                        fromIndex++;
                    }
                    //here count has number of rows
                    databasehelper db = new databasehelper(service.this);
                    table[] t = new table[count + 1];
                    //cut string from start value to end fo string and new string is that
                    String actual = output.substring(startvalue);

                    for (int i = 1; i < count + 1; i++) {
                        //parse string and add to database
                        String temp = actual.split("Temperature\":\"")[i].split("\"")[0];
                        String hum = actual.split("Humidity\":\"")[i].split("\"")[0];
                        t[i] = new table(-1, temp, hum);
                        db.addOne(t[i]);
                    }


                    SharedPreferences.Editor edit = sp.edit();
                    edit.putInt(idname, output.length());
                    edit.apply();
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

        }

    }
}


