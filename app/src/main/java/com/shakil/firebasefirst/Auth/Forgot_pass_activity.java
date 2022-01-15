package com.shakil.firebasefirst.Auth;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.shakil.firebasefirst.R;

public class Forgot_pass_activity extends AppCompatActivity {


    private EditText editText;
    private Button button;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    private AdView mAdView;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass_activity);


        editText=findViewById(R.id.forgot_edit_id);
        button=findViewById(R.id.forget_button_id);
        progressBar=findViewById(R.id.progress_id);


        firebaseAuth=FirebaseAuth.getInstance();


        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.sendPasswordResetEmail(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Check Your Email To Reset Your Password.",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Forgot_pass_activity.this, MainActivity.class));
                            finish();
                        }
                        else
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
