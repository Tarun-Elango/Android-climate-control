package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.abego.treelayout.internal.util.java.lang.string.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//this activity is used for testing the connection to website url
//and fetching the json string
public class readwebsite extends AppCompatActivity {
    ProgressDialog pd;
    protected TextView tv;
    public static final String shared_p= "myfile";
    public static final String  idname = "id";

    String output=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readwebsite);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv = findViewById(R.id.textView7);

        SharedPreferences.Editor editor = getSharedPreferences(shared_p, MODE_PRIVATE).edit();

        SharedPreferences prefs = getSharedPreferences(shared_p, MODE_PRIVATE);
        int check = prefs.getInt(idname,0);


        if(check== 0) {


            editor.putInt(idname, 1);
            editor.apply();
        }
threadwork t= new threadwork();
t.start();
}
 class threadwork extends Thread{
     String inputLine;
     @Override
     public void run() {
         super.run();
         String url="https://opensheet.vercel.app/16IyDyTCPeNuPWu44DPaqkSylx6csESJcsI3g4mMmEac/sheet1";
         URL obj = null;
         try {
             obj = new URL(url);
         } catch (MalformedURLException e) {
             e.printStackTrace();
         }
         HttpURLConnection con = null;
         try {
             con = (HttpURLConnection) obj.openConnection();
         } catch (IOException e) {
             e.printStackTrace();
         }
         try {
             con.setRequestMethod("GET");
         } catch (ProtocolException e) {
             e.printStackTrace();
         }
         con.setRequestProperty("User-Agent", "Mozilla/5.0");
         try {
             int responseCode = con.getResponseCode();
         } catch (IOException e) {
             e.printStackTrace();
         }
         BufferedReader in = null;
         try {
             in = new BufferedReader(
                     new InputStreamReader(con.getInputStream()));
         } catch (IOException e) {
             e.printStackTrace();
         }

         StringBuffer response = new StringBuffer();
         while (true) {
             try {
                 if (!((inputLine = in.readLine()) != null)) break;
             } catch (IOException e) {
                 e.printStackTrace();
             }
             response.append(inputLine);
         }
         try {
             in.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
         String output =response.toString();

         SharedPreferences sp = getSharedPreferences(shared_p, MODE_PRIVATE);
         int startvalue = sp.getInt(idname, -1);
         if(startvalue != output.length()){
             String strFind = "Date";
             int count = 0, fromIndex = startvalue;
             while ((fromIndex = output.indexOf(strFind, fromIndex)) != -1 ){
                 count++;
                 fromIndex++;
             }
                //here count has number of rows
             databasehelper db = new databasehelper(readwebsite.this);
             table[] t= new table[count+1];
             //cut string from start value to end fo string and new string is that
             String actual = output.substring(startvalue);

             for(int i=1;i<count+1;i++) {
                 String temp = actual.split("Temperature\":\"")[i].split("\"")[0];
                 String hum = actual.split("Humidity\":\"")[i].split("\"")[0];
                 t[i] = new table(-1, temp, hum);
                 db.addOne(t[i]);
             }
             SharedPreferences.Editor edit = sp.edit();
             edit.putInt(idname, output.length());
             edit.apply();


             }


     }



 }

    }


