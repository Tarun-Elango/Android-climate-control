package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Base64;

//test class
public class Register extends AppCompatActivity {
protected EditText ename;
protected EditText epass;
protected Button bsave;
    public static final String shared_pref = "details";
    public static final String  n = "name";
    public static final String p= "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    ename = findViewById(R.id.editTextTextPersonName2);
    epass= findViewById(R.id.editTextTextPersonName3);
    bsave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = ename.getText().toString();
            String pass = epass.getText().toString();
            SharedPreferences sp = getSharedPreferences(shared_pref, MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            String Nameencrypt = Encrypt(name);
            String Passencrpyt = Encrypt(pass);
            edit.putString(n, Nameencrypt);
            edit.putString(p,Passencrpyt);
            edit.apply();
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            //open mainactivity

        }
    });

    }public String Encrypt(String plain) {
        String encoded = Base64.getEncoder().encodeToString(plain.getBytes());

        // Reverse the string
        String reverse = new StringBuffer(encoded).reverse().toString();

        StringBuilder tmp = new StringBuilder();
        final int OFFSET = 4;
        for (int i = 0; i < reverse.length(); i++) {
            tmp.append((char)(reverse.charAt(i) + OFFSET));
        }
        return tmp.toString();
    }

}