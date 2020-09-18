package com.shakil.firebasefirst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Search_Donet extends AppCompatActivity {

    Button serchButton,GiveButton,Update;
    FirebaseAuth mauth;
    SharedPreferenceLogin sharedPreferenceLogin;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search__donet);
        databaseReference = FirebaseDatabase.getInstance().getReference();

         serchButton=findViewById(R.id.SerchButton);
         GiveButton=findViewById(R.id.GiveButton);
         Update=findViewById(R.id.update_id);
         mauth=FirebaseAuth.getInstance();
        sharedPreferenceLogin = new SharedPreferenceLogin(this);

        serchButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(Search_Donet.this,Search.class));


             }
         });
         GiveButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(Search_Donet.this,Home_activity.class));

             }
         });
         Update.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 databaseReference.child("users").child(mauth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         if(dataSnapshot.exists()) {
                             startActivity(new Intent(getApplicationContext(), Update_date.class));
                         }
                         else {
                             Toast.makeText(getApplicationContext(),"Save data first.",Toast.LENGTH_LONG).show();
                         }

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });


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
            startActivity(new Intent(Search_Donet.this,MainActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
