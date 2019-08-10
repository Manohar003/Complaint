package com.example.compalintapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class splashscreen extends AppCompatActivity {

    String value,value1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("Validation", Context.MODE_PRIVATE);
        value = sharedPreferences.getString("Username", "none");
        value1 = sharedPreferences.getString("Password","none");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               if(value=="none"&&value1=="none") {
                   Intent intent1 = new Intent(splashscreen.this, MainActivity.class);
                   startActivity(intent1);
                   finish();
                }
               else
             {
                 Intent intent=new Intent(splashscreen.this,compact.class);
                 startActivity(intent);
                 finish();
                }
            }
        }, 5000);


    }
}
