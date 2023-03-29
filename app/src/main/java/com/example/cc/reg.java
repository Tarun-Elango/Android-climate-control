package com.example.a390cc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Base64;

//activity for when the user registers at first
public class reg extends AppCompatActivity {
protected EditText name;
protected EditText pass;
protected Button store;
    public static final String shared_pref = "details";
    public static final String  n = "name";
    public static final String p= "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
name = findViewById(R.id.name_place);
pass = findViewById(R.id.pass_place);
store = findViewById(R.id.button_store);

store.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String namevalue = name.getText().toString();
        String passvalue = pass.getText().toString();
        SharedPreferences sp = getSharedPreferences(shared_pref, MODE_PRIVATE);
        SharedPreferences.Editor edit  = sp.edit();
        String en = Encrypt(namevalue);
        String ep = Encrypt(passvalue);
        edit.putString(n,en);
        edit.putString(p,ep);
        edit.apply();
        Intent intent = new Intent(reg.this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(reg.this, "New user credentials set",Toast.LENGTH_LONG).show();


    }
});


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
}