package com.example.a390cc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.List;

//this class has the back-end for past data function
//it takes the values from the sqlite database and stores them in a arraylist before
//displaying it in a line graph format

public class analytics extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private LineGraphSeries<DataPoint> seriesTemp, seriesHumidity;
    public static final String shared_pref = "analytics";
    public static final String  t = "temp";
    public static final String h= "hum";
   // protected Spinner spin = findViewById(R.id.spin);
boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Spinner spin = findViewById(R.id.spinner);
//get the values from the database into a list
        databasehelper db = new databasehelper(analytics.this);
        List<Double> temp = db.gettemparray();
        List<Double> hum = db.gethumarray();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(analytics.this, R.array.list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(analytics.this);

//the following is a bunch of test values to test of joe64 graphview
        double x_temp,y_temp;
        double x_hum,y_hum;
        x_temp=0.0;
        x_hum=0.0;
        //TODO: Can store y data in this array
        double[] y_arrayTemp = new double[]{22, 23, 22, 21, 22,23,23,22,22.5,23.1};
        double[] y_arrayHum = new double[]{50, 60, 62, 66, 70,63,61,66,80,59};
        //Set up
        GraphView GraphTemp= (GraphView) findViewById(R.id.graphTemp);
        GraphView GraphHumidity= (GraphView) findViewById(R.id.graphHumidity);
        seriesTemp = new LineGraphSeries<DataPoint>();
        seriesHumidity = new LineGraphSeries<DataPoint>();

        int dataPointsCount =temp.size();
        //Insert dataPoints in the series
        for (int i=0; i< dataPointsCount; i++){
            //For temperature
            y_temp= temp.get(i);
            //     y_arrayTemp[i];
            seriesTemp.appendData(new DataPoint(x_temp,y_temp), true, 1000);
            x_temp++;
            //for humidity
            y_hum= hum.get(i);
            //y_arrayHum[i];
            seriesHumidity.appendData(new DataPoint(x_hum,y_hum), true, 1000);
            x_hum++;
        }

        //the following are some functions that set the graphviews

        GraphTemp.addSeries(seriesTemp);
        GraphHumidity.addSeries(seriesHumidity);
        GraphTemp.getViewport().setScrollable(true);
        GraphTemp.getViewport().setMaxX(dataPointsCount);


        GraphTemp.getViewport().setMaxY(100);
        GraphTemp.getViewport().setXAxisBoundsManual(true);
        GraphTemp.getViewport().setYAxisBoundsManual(true);

        GraphHumidity.getViewport().setScrollable(true);
        GraphHumidity.getViewport().setMaxX(dataPointsCount);
        GraphHumidity.getViewport().setMaxY(100);
        GraphHumidity.getViewport().setXAxisBoundsManual(true);
        GraphHumidity.getViewport().setYAxisBoundsManual(true);


                    SharedPreferences spx = getSharedPreferences(shared_pref, MODE_PRIVATE);
                    int minXLimitt = Integer.parseInt(spx.getString(t,null));

                    int minXLimith = Integer.parseInt(spx.getString(h,null));
                    GraphTemp.getViewport().setMinX(minXLimitt);
                    GraphHumidity.getViewport().setMinX(minXLimith);


        //Graph style
        GraphTemp.setTitle("Temperature");
        GraphTemp.setTitleColor(R.color.teal_700);
        GraphTemp.getGridLabelRenderer().setHorizontalAxisTitle("Time (s)");
        GraphTemp.getGridLabelRenderer().setVerticalAxisTitle("Temperature ("+"\u00B0"+"C)");

        GraphHumidity.setTitle("Humidity");
        GraphHumidity.setTitleColor(R.color.teal_700);
        GraphHumidity.getGridLabelRenderer().setHorizontalAxisTitle("Time (s)");
        GraphHumidity.getGridLabelRenderer().setVerticalAxisTitle("Humidity (%)");


        //get the data point when tapping the graph
        seriesTemp.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(analytics.this, "Temperature: Data Point clicked: "+ dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        seriesHumidity.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(analytics.this, "Humidity: Data Point clicked: "+ dataPoint, Toast.LENGTH_SHORT).show();
            }
        });


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{


                }
                catch (Exception e) {

                }
                finally{
                   handler.postDelayed(this, 1000);
                }
            }
        };

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.analytics_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menudot:


              //make a shared preference and set the values temp and hum
                SharedPreferences sp = getSharedPreferences(shared_pref, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(t, "0");
                edit.putString(h,"0");
                edit.apply();
                //  Toast.makeText(analytics.this, "value set", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }


    //this sets the limit for the graphs x axis when the user changes the time limit
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        SharedPreferences spt = getSharedPreferences(shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor edit3 = spt.edit();
        //use databasehelper to get the total rows
        databasehelper db = new databasehelper(analytics.this);
        if(db.totalCount()>30000){
            if(text.equals( "All")){
                edit3.putString(t,"0");
                edit3.putString(h,"0");
                edit3.apply();
                edit3.apply();
                finish();
                startActivity(getIntent());

            }
            else  if (text.equals("last Day")){
                int newvalue = db.totalCount()-17280;
                String Snewvalue = Integer.toString(newvalue);
                //last value - 17280
                edit3.putString(t,Snewvalue);
                edit3.putString(h,Snewvalue);
                edit3.apply();
                finish();
                startActivity(getIntent());

            }
            else  if (text.equals("last hour")){
                //last - 720
                int newvalue = db.totalCount()-720;
                String Snewvalue = Integer.toString(newvalue);
                edit3.putString(t,"10");
                edit3.putString(h,"10");
                edit3.apply();
                finish();
                startActivity(getIntent());

            }
            else if (text.equals("last 10 minutes")){
                //last - 120
                int newvalue = db.totalCount()-120;
                String Snewvalue = Integer.toString(newvalue);
                edit3.putString(t,"5");
                edit3.putString(h,"5");
                edit3.apply();
                finish();
                startActivity(getIntent());
            }
        }
        else{
if(text.equals( "All")){
edit3.putString(t,"0");
edit3.putString(h,"0");
edit3.apply();
    edit3.apply();
    finish();
    startActivity(getIntent());

}
else  if (text.equals("last Day")){
    //last value - 17280
    edit3.putString(t,"50");
    edit3.putString(h,"10");
    edit3.apply();
    finish();
    startActivity(getIntent());

}
else  if (text.equals("last hour")){
    //last - 720
    edit3.putString(t,"100");
    edit3.putString(h,"25");
    edit3.apply();
    finish();
    startActivity(getIntent());

}
else if (text.equals("last 10 minutes")){
    //last - 120
    edit3.putString(t,"110");
    edit3.putString(h,"110");
    edit3.apply();
    finish();
    startActivity(getIntent());
}

Toast.makeText(adapterView.getContext(), text,Toast.LENGTH_SHORT).show();
    }}

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
