package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//activity for the notification page


public class notify extends AppCompatActivity {
    protected Button test;
    protected Button save;
    protected EditText text1;
    protected EditText text2;
    protected EditText text3;
    protected EditText text4;
    public static final String shared_pref = "storetemp";
    public static final String  n = "up";
    public static final String p= "down";
    public static final String shared_pref2 = "storehum";
    public static final String  n2 = "hup";
    public static final String p2= "hdown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        test = findViewById(R.id.button_test);
        save = findViewById(R.id.button_save);

        text1 = findViewById(R.id.editTextNumber);
        text2 = findViewById(R.id.editTextNumber2);
        text3 = findViewById(R.id.editTextNumber3);
        text4 = findViewById(R.id.editTextNumber4);

//function to set the notification
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (text2.getText().toString().equals("") || text1.getText().toString().equals("") || text4.getText().toString().equals("") || text3.getText().toString().equals("")) {
                    Toast.makeText(notify.this, "Enter all values", Toast.LENGTH_SHORT).show();
                } else {
                    Float tempup = Float.parseFloat(text2.getText().toString());
                    Float tempdown = Float.parseFloat(text1.getText().toString());
                    Float humup = Float.parseFloat(text4.getText().toString());
                    Float humdown = Float.parseFloat(text3.getText().toString());
                    SharedPreferences sp = getSharedPreferences(shared_pref, MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putFloat(n, tempdown);
                    edit.putFloat(p, tempup);
                    edit.apply();
                    //set the shared preference for the user input value
                    SharedPreferences sp2 = getSharedPreferences(shared_pref2, MODE_PRIVATE);
                    SharedPreferences.Editor edit2 = sp2.edit();
                    edit2.putFloat(n2, humdown);
                    edit2.putFloat(p2, humup);
                    edit2.apply();
                    startService(new Intent(notify.this, notify_service.class));
                }


            }
        });

//function to test the notification
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.Builder bui = new NotificationCompat.Builder(notify.this, "noti").setSmallIcon(R.drawable.ic_baseline_add_alert_24).setContentTitle("Climate Control")
                        .setContentText("cleared the range limit")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                NotificationManagerCompat nm = NotificationManagerCompat.from(notify.this);
                nm.notify(50, bui.build());

                //clear the shared preference
                SharedPreferences sp = getSharedPreferences(shared_pref, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                SharedPreferences sp2 = getSharedPreferences(shared_pref2, MODE_PRIVATE);
                SharedPreferences.Editor edit2 = sp2.edit();
                edit.clear();
                edit.apply();
                edit2.clear();
                edit2.apply();
                // stopService(new Intent(notify.this, notify_service.class));
            }

        });

    }

    //testing notification
        private void notichan () {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence n = "notifychan";
                String desc = "inform user";
                int importan = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("noti", n, importan);
                channel.setDescription(desc);

                NotificationManager nM = getSystemService(NotificationManager.class);
                nM.createNotificationChannel(channel);
            }
        }



}