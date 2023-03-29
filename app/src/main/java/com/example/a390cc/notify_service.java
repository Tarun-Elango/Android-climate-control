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


//service class for notification feature
public class notify_service extends Service {
    public static final String shared_pref = "storetemp";
    public static final String  n = "up";
    public static final String p= "down";
    public static final String shared_pref2 = "storehum";
    public static final String  n2 = "hup";
    public static final String p2= "hdown";
  protected static boolean check;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       notichan();
        //start the notification thread
       notif nt = new notif();
       nt.start();

            return START_STICKY;

    }


    private void notichan(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence n = "notifychan";
            String desc ="inform user";
            int importan = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("noti", n,importan);
            channel.setDescription(desc);

            NotificationManager nM = getSystemService(NotificationManager.class);
            nM.createNotificationChannel(channel);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    //notification thread class
    //check boolean variable for resetting once notification is shown
    class notif extends Thread{
        @Override
        public void run() {
            super.run();

          check= true;
            while (check!=false) {
                SharedPreferences sp = getSharedPreferences(shared_pref, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                Float tempup_check = sp.getFloat(p, -1);
                Float tempdown_Check = sp.getFloat(n, -1);
                int tuc = Math.round(tempup_check);
                int tdc = Math.round(tempdown_Check);
                SharedPreferences sp2 = getSharedPreferences(shared_pref2, MODE_PRIVATE);
                SharedPreferences.Editor edit2 = sp2.edit();
                Float humup_check = sp2.getFloat(p2, -1);
                Float humdown_Check = sp2.getFloat(n2, -1);
                int huc = Math.round(humup_check);
                int hdc = Math.round(humdown_Check);

                databasehelper db = new databasehelper(notify_service.this);

                int tempValue = new Double(db.getlast()).intValue();
                int HumValue = new Double(db.getlasthum()).intValue();;

                if (tuc < tempValue || tdc > tempValue) {

                    NotificationCompat.Builder bui = new NotificationCompat.Builder(notify_service.this, "noti").setSmallIcon(R.drawable.ic_baseline_add_alert_24).setContentTitle("Climate Control")
                            .setContentText("the temperature sensor readings have gone beyond the range")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                    NotificationManagerCompat nm = NotificationManagerCompat.from(notify_service.this);
                    nm.notify(50, bui.build());
                    edit.clear();
                    edit.apply();
                    check= false;
                }
                if (huc < HumValue || hdc > HumValue) {
                    NotificationCompat.Builder bui = new NotificationCompat.Builder(notify_service.this, "noti").setSmallIcon(R.drawable.ic_baseline_add_alert_24).setContentTitle("Climate Control")
                            .setContentText("the humidity sensor readings have gone beyond the range")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                    NotificationManagerCompat nm = NotificationManagerCompat.from(notify_service.this);
                    nm.notify(50, bui.build());
                    edit.putFloat(n2,-1);
                    edit2.clear();
                    edit2.apply();
                    check= false;
                }

            }
        }
    }

}
