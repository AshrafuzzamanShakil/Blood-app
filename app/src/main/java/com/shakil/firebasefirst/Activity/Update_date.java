package com.shakil.firebasefirst.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shakil.firebasefirst.Adapter.Class.Doner;
import com.shakil.firebasefirst.R;

import java.util.Calendar;

public class Update_date extends AppCompatActivity {

    private TextView update_date_text;
    private Button update_button;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    private ProgressBar progressBar;
    DatePickerDialog datePickerDialog;
    private int number;
    private AdView mAdView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_date);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        progressBar=findViewById(R.id.progress_id);
        update_date_text=findViewById(R.id.Update_lastdate_id);
        update_button=findViewById(R.id.Update_date_id);

        MobileAds.initialize(this);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        update_date_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                final int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH);
                final int day=calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Update_date.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int i, int j, int k) {
                                update_date_text.setText(k+"-"+(j+1)+"-"+i);

                            }
                        },year,month,day);

                datePickerDialog.show();
            }
        });

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getUid());


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date=update_date_text.getText().toString();
                if(date.isEmpty())
                {
                    update_date_text.setError("Please select a date");
                    update_date_text.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Doner doner=dataSnapshot.getValue(Doner.class);
                        number=doner.getNumber_of_donation()+1;
                        doner.setNumber_of_donation(number);
                        doner.setDate(date);
                        databaseReference.setValue(doner);
                        progressBar.setVisibility(View.GONE);

                        Toast.makeText(getApplicationContext(),"Date Updated",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(Update_date.this, Search_Donet.class));
                        finish();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }
}
