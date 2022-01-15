package com.shakil.firebasefirst.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.shakil.firebasefirst.Adapter.DonerList;
import com.shakil.firebasefirst.R;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {
    FirebaseAuth mauth;
    ListView listView;
    Spinner spinner,editText;
    Button button;
    DatabaseReference rff;
    List<Doner> donerList;
    ProgressBar progressBar;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mauth=FirebaseAuth.getInstance();
        listView=findViewById(R.id.listviewId);
        editText=findViewById(R.id.editserch);
        button=findViewById(R.id.Buttonserch);
        progressBar=findViewById(R.id.progressid);
        spinner=findViewById(R.id.spinerid);

        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Area,R.layout.spiner_layout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence>adapterBlood=ArrayAdapter.createFromResource(this,R.array.Blood_Group,R.layout.spiner_layout);
        adapterBlood.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        editText.setAdapter(adapterBlood);

        rff= FirebaseDatabase.getInstance().getReference("users");
        donerList=new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getSelectedItem().toString().contentEquals("Blood Group")|| spinner.getSelectedItem().toString().contentEquals("Area"))
                {
                    editText.requestFocus();
                    spinner.requestFocus();
                    Toast.makeText(getApplicationContext(),"Please Seclect Blood Group and Area",Toast.LENGTH_LONG).show();
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                rff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        donerList.clear();

                            for (DataSnapshot v : dataSnapshot.getChildren()) {
                                Doner doner = v.getValue(Doner.class);

                                if ((doner.getBloodGroup().toString().trim()).contentEquals(editText.getSelectedItem().toString().trim())
                                        && doner.getArea().toString().trim().contentEquals(spinner.getSelectedItem().toString().trim())) {
                                    donerList.add(doner);
                                    spinner.setVisibility(View.GONE);
                                    editText.setVisibility(View.GONE);
                                    button.setVisibility(View.GONE);

                                }


                            }

                            progressBar.setVisibility(View.GONE);
                            DonerList adapter = new DonerList(Search.this, donerList);
                            listView.setAdapter(adapter);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Doner doner=donerList.get(position);
                String phone ="tel:"+doner.getPhone();
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);

            }
        });

    }


}
