package com.shakil.firebasefirst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class Home_activity extends AppCompatActivity {
    FirebaseAuth mauth;
    private EditText name, phone;
    TextView last_date;
    private Spinner spinner;
    private Spinner bloodGroup;
    public TextView save,show;
    private ImageButton imageButton;
    DatabaseReference databaseReference;
    private int Galary_intent = 2;
    Uri FilePathUri;
    StorageReference storageReference;
    Uri downloadUri;
    DatePickerDialog datePickerDialog;
    LinearLayout linearLayout;
    private int number_of_donation=0;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_activity);
        mauth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference=FirebaseStorage.getInstance().getReference("Images");

        imageButton = findViewById(R.id.imageButton);
        last_date=findViewById(R.id.lastdate_id);

        name = findViewById(R.id.nameid);
        phone = findViewById(R.id.ageid);
        save = findViewById(R.id.saveid);
        spinner = findViewById(R.id.spinerid);
        bloodGroup = findViewById(R.id.bloodgroupid);
        show=findViewById(R.id.showdata);
        linearLayout=findViewById(R.id.linlay);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Area, R.layout.spiner_layout);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> adapterBlood = ArrayAdapter.createFromResource(this, R.array.Blood_Group, R.layout.spiner_layout);
        adapterBlood.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        bloodGroup.setAdapter(adapterBlood);
        downloadUri=Uri.parse("shakil");
        databaseReference.child("users").child(mauth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    linearLayout.setVisibility(View.GONE);
                    imageButton.setVisibility(View.GONE);
                    show.setVisibility(View.VISIBLE);
                    show.setText("Your data is already saved.");


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        last_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance();
                final int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH);
                final int day=calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(Home_activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int i, int j, int k) {
                        last_date.setText(k+"-"+(j+1)+"-"+i);

                    }
                },year,month,day);

                datePickerDialog.show();

            }
        });


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Galary_intent);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
            }
        });


    }


    private void savedata() {


        final String nametext = name.getText().toString().trim();
        final String phonetext = phone.getText().toString().trim();
        final String bloodtext = bloodGroup.getSelectedItem().toString().trim();
        final String area = spinner.getSelectedItem().toString().trim();
        final String date=last_date.getText().toString();


        if (nametext.isEmpty()) {
            name.setError("Name is empty");
            name.requestFocus();
            return;
        }
        if (phonetext.isEmpty()) {
            phone.setError("Phone is empty");
            phone.requestFocus();
            return;
        }
        if (bloodtext.contentEquals("Blood Group")) {
            bloodGroup.requestFocus();
            return;
        }
        if (area.contentEquals("Area")) {
            spinner.requestFocus();
            return;
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try{
                        Doner doner = new Doner(nametext, phonetext, bloodtext, area, downloadUri.toString(),date,number_of_donation);
                        databaseReference.child("users").child(mauth.getUid()).setValue(doner);
                        Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_LONG).show();
                        name.setText("");
                        phone.setText("");
                        last_date.setText("");


                }catch (Exception e) {
                    e.getMessage();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.log_out, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logoutId) {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(Home_activity.this, MainActivity.class));
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Galary_intent && resultCode == RESULT_OK && data != null && data.getData() != null) {
            final Uri uri = data.getData();
            imageButton.setImageURI(uri);
            FilePathUri = data.getData();

            final StorageReference imagename=storageReference.child("image"+uri.getLastPathSegment());

            imagename.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUri=uri;

                        }
                    });

                }
            });






        }


    }

}
