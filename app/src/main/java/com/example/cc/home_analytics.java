package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

//this activity is for the home analytics and gives suggestions
public class home_analytics extends AppCompatActivity {
    protected TextView t26;
    protected TextView t20suggestion;
    protected  TextView t27;
    protected TextView t28;
    protected TextView t33;
    protected TextView t34;

    //shared preference that has the latest weather
    public static final String shared_prefwet = "weather";
    public static final String  tem = "temp";
    public static final String hum= "hum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_analytics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    t26 = findViewById(R.id.textView26);
    t20suggestion= findViewById(R.id.textView20);
    t27 = findViewById(R.id.textView22);
    t28 = findViewById(R.id.textView23);
    t33 = findViewById(R.id.textView28);
    t34= findViewById(R.id.textView33);
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                   //handler to set the suggestion
                    databasehelper db = new databasehelper(home_analytics.this);

                    int temp= new Double(db.getlast()).intValue();
                    int Hum = new Double(db.getlasthum()).intValue();;

                    if (temp<15){
                        if(Hum<40){
                            t26.setText("keep temperature high");
                            t26.setTextColor(Color.GREEN);
                            t20suggestion.setText("no changes required");
                            t20suggestion.setTextColor(Color.GREEN);

                        }
                        else{
                            t26.setText("keep temperature high");
                            t26.setTextColor(Color.GREEN);
                            t20suggestion.setText("Reduce Humidity");
                            t20suggestion.setTextColor(Color.BLUE);
                        }
                    }
                    else if(temp<30 ){
                        if(Hum<40){
                            t26.setText("normal temperature and humidity levels");
                            t26.setTextColor(Color.GREEN);
                            t20suggestion.setText("no changes required");
                            t20suggestion.setTextColor(Color.GREEN);

                        }
                        else{
                            t26.setText("normal temperature levels");
                            t26.setTextColor(Color.GREEN);
                            t20suggestion.setText("Reduce Humidity");
                            t20suggestion.setTextColor(Color.BLUE);
                        }

                    }
                    else if(temp>31 || temp<35){
                        if(Hum>40 || Hum <60){
                            t26.setText("Take Extreme caution");
                            t26.setTextColor(Color.RED);
                            t20suggestion.setText("Decrease Humidity");
                            t20suggestion.setTextColor(Color.RED);
                        }
                        else if(Hum>60){
                            t26.setText("Take Extreme caution");
                            t26.setTextColor(Color.RED);
                            t20suggestion.setText("Decrease Humidity ");
                            t20suggestion.setTextColor(Color.RED);
                        }
                        else if(Hum<40){
                            t26.setText("Take caution");
                            t26.setTextColor(Color.RED);
                            t20suggestion.setText("Decrease Temperature ");
                            t20suggestion.setTextColor(Color.RED);
                        }


                    }
                    else if(temp>35 || temp <40){
                        if(Hum>60){
                            t26.setText("Dangerous levels");
                            t26.setTextColor(Color.RED);
                            t20suggestion.setText("Decrease Humidity and Temperature");
                            t20suggestion.setTextColor(Color.RED);
                        }
                        else{
                            t26.setText("Dangerous levels");
                            t26.setTextColor(Color.RED);
                            t20suggestion.setText("Decrease Temperature");
                            t20suggestion.setTextColor(Color.RED);
                        }

                    }
                    else if(temp>42){
                             t26.setText("Extremely Dangerous levels");
                            t26.setTextColor(Color.RED);
                            t20suggestion.setText("Decrease Humidity and Temperature");
                            t20suggestion.setTextColor(Color.RED);


                    }

                    SharedPreferences sp = getSharedPreferences(shared_prefwet, MODE_PRIVATE);
                    int temperature = new Double(sp.getString(tem, "")).intValue();
                    int tc = temperature-273;

                 t27.setText(String.valueOf(tc)+" °C");


                    String hums = sp.getString(hum, "").replaceAll("\\}", "");
                        t28.setText(hums+" %");


                    if(tc<20){

                        int increase = 20-tc;
                             t33.setText("Keep temperature High");
                             t33.setTextColor(Color.BLUE);
                    }
                    else if(tc>20){
                        int decrease = tc-20;
                        t33.setText("Keep temperature low");
                        t33.setTextColor(Color.RED);
                    }
                    int humidity = Integer.parseInt(hums);
                    if(humidity<50){
                        int increase = 50-humidity;
                        t34.setText("Keep humidity High");
                        t34.setTextColor(Color.BLUE);
                    }
                    else if(humidity>50)
                    {
                       // int decrease = tc-20;
                        t34.setText("Keep humidity low");
                        t34.setTextColor(Color.RED);
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
}