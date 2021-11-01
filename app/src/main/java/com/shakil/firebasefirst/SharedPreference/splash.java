package com.shakil.firebasefirst.SharedPreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.shakil.firebasefirst.Auth.MainActivity;
import com.shakil.firebasefirst.R;

public class splash extends AppCompatActivity {


    public static int Splash_Time_Out=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(splash.this, MainActivity.class));

                finish();

            }
        },Splash_Time_Out);


    }






}
