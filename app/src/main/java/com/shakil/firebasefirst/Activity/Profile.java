package com.shakil.firebasefirst.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.shakil.firebasefirst.Adapter.Class.Doner;
import com.shakil.firebasefirst.Auth.MainActivity;
import com.shakil.firebasefirst.R;
import com.shakil.firebasefirst.SharedPreference.SharedPreferenceLogin;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {
    SharedPreferenceLogin sharedPreferenceLogin;
    CircularImageView circularImageView;
    ImageView badge_image;
    TextView name,blood_grpup,last_date_of_donation,area;
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getUid());
        circularImageView=findViewById(R.id.profial_pic_id);
        badge_image=findViewById(R.id.profile_bazeid);
        area=findViewById(R.id.profile_area);
        name=findViewById(R.id.profile_name);
        blood_grpup=findViewById(R.id.profile_blood_group);
        last_date_of_donation=findViewById(R.id.profile_donation_date);
        button=findViewById(R.id.profial_ser_don);
        sharedPreferenceLogin = new SharedPreferenceLogin(this);


        MobileAds.initialize(this,"ca-app-pub-4187023831811200~6769786275"

        );

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this, Search_Donet.class));

            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Doner doner=dataSnapshot.getValue(Doner.class);
                    name.setText(doner.getName());
                    blood_grpup.setText(doner.getBloodGroup());
                    last_date_of_donation.setText(doner.getDate());
                    area.setText(doner.getArea());
                    Picasso.get().load(doner.getImagepath().toString()).into(circularImageView);

                    if(doner.getNumber_of_donation()<10)
                    {
                        badge_image.setImageResource(R.drawable.bronze);
                    }
                    if(doner.getNumber_of_donation()>=10&&doner.getNumber_of_donation()<25)
                    {
                        badge_image.setImageResource(R.drawable.silver);
                    }
                    if (doner.getNumber_of_donation()>=25)
                    {
                        badge_image.setImageResource(R.drawable.gold);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutId)
        {
            sharedPreferenceLogin.writeRegistrationinStatus(false);
            startActivity(new Intent(Profile.this, MainActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
