package com.shakil.firebasefirst;

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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText emailtext,passtext;
    Button login,sinup;
    TextView forgot;
    SharedPreferenceLogin sharedPreferenceLogin;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth= FirebaseAuth.getInstance();

        emailtext=findViewById(R.id.editText);
        passtext=findViewById(R.id.editText2);
        sinup=findViewById(R.id.button2);
        login=findViewById(R.id.button);
        progressBar=findViewById(R.id.progressid);
        forgot=findViewById(R.id.forgotid);


        sharedPreferenceLogin = new SharedPreferenceLogin(this);


        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SingUp_activity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogin();

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Forgot_pass_activity.class));

            }
        });


    }

    private void UserLogin() {
        String email=emailtext.getText().toString().trim();
        String pass=passtext.getText().toString().trim();

        if(email.isEmpty())
        {
            emailtext.setError("email is empty");
            emailtext.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            passtext.setError("password is empty");
            passtext.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful())
                {
                    if(mAuth.getCurrentUser().isEmailVerified()){
                        progressBar.setVisibility(View.GONE);
                        sharedPreferenceLogin.writeRegistrationinStatus(true);
                        startActivity(new Intent(MainActivity.this,Search_Donet.class));
                        finish();
                        Toast.makeText(getApplicationContext(),"You are logged in.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Please Chack Your Email And Verify First.",Toast.LENGTH_LONG).show();
                    }

                }
                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"Make sure your email and password is correct.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }




    @Override
    protected void onStart() {
        super.onStart();


        if (sharedPreferenceLogin.readRegistrationString()) {
            Intent intent = new Intent(getApplicationContext(), Search_Donet.class);
            startActivity(intent);
            finish();
        }
    }
}
