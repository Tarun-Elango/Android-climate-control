package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Base64;

//class for the login page
public class login extends AppCompatActivity {
    protected EditText namefield;
    protected EditText password;
    protected Button register;
    protected Button login;
    protected  Button save;
    public static final String shared_pref = "details";
    public static final String  n = "name";
    public static final String p= "password";
    protected boolean check = false;
    public static final String shared_prefa = "analytics";
    public static final String  t = "temp";
    public static final String h= "hum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        namefield= findViewById(R.id.editTextTextPersonName);
        password= findViewById(R.id.editTextTextPassword);
        register= findViewById(R.id.button_REG);
        login= findViewById(R.id.button_LOGIN);
        ConstraintLayout constraintLayout= findViewById(R.id.layout);

//register button and its functions
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = namefield.getText().toString();
                String pass = password.getText().toString();
                SharedPreferences sp = getSharedPreferences(shared_pref, MODE_PRIVATE);

                String name_check = sp.getString(n, null);
                String pass_Check = sp.getString(p,null);
                if(name_check== null && pass_Check == null){
                    SharedPreferences.Editor edit  = sp.edit();
                    String en = Encrypt(name);
                    String ep = Encrypt(pass);
                    edit.putString(n,en);
                    edit.putString(p,ep);
                    edit.apply();
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                }
                else {

                    if (name.equals(Decrypte(name_check)) && pass.equals(Decrypte(pass_Check))) {
                        Intent intent = new Intent(login.this, reg.class);

                        startActivity(intent);
                    } else {
                        Toast.makeText(login.this, "user does not exist", Toast.LENGTH_LONG).show();
                    }


                }
            }


        });

//login button and its functions
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check in sp and open main if exists
                String name = namefield.getText().toString();
                String pass = password.getText().toString();
                SharedPreferences sp = getSharedPreferences(shared_pref, MODE_PRIVATE);
                String name_check = sp.getString(n, "");
                String pass_Check = sp.getString(p,"");
                if(name.equals(Decrypte(name_check)) && pass.equals(Decrypte(pass_Check))){
                    Intent intent = new Intent(login.this, MainActivity.class);
                   startActivity(intent);

                }
                else{
                    Toast.makeText(login.this, "user does not exist",Toast.LENGTH_LONG).show();
                }
            }
        });
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    //handler for animation
                    AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
                    animationDrawable.setEnterFadeDuration(2500);
                    animationDrawable.setExitFadeDuration(5000);
                    animationDrawable.start();

                    databasehelper db = new databasehelper(login.this);

                }
                catch (Exception e) {

                }
                finally{

                    handler.postDelayed(this, 1000);
                }
            }
        };


        handler.post(runnable);

        //shared preference for analytics, used to set the x-axis limit for the two graphs
        SharedPreferences spx = getSharedPreferences(shared_prefa, MODE_PRIVATE);
        SharedPreferences.Editor edit  = spx.edit();
        edit.putString(t,"0");
        edit.putString(h,"0");
        edit.apply();
    }
    public String Encrypt(String change) {
        String encode ;
        String letter;
        encode = Base64.getEncoder().encodeToString(change.getBytes());
        letter = new StringBuffer(encode).reverse().toString();
        StringBuilder output = new StringBuilder();
        final int t = 4;
        for (int i = 0; i < letter.length(); i++) {
            output.append((char)(letter.charAt(i) + t));}
        return output.toString();
    }
    public String Decrypte(String change1) {
        StringBuilder decode = new StringBuilder();
        final int t = 4;
        for (int i = 0; i < change1.length(); i++) {
           decode.append((char)(change1.charAt(i) - t)); }
        String output= new StringBuffer(decode.toString()).reverse().toString();
        return new String(Base64.getDecoder().decode(output));
    }





}