package com.shakil.firebasefirst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SingUp_activity extends AppCompatActivity {
    EditText emailtext,passtext;
    Button sinup;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up_activity);
        mAuth= FirebaseAuth.getInstance();

        emailtext=findViewById(R.id.editText);
        passtext=findViewById(R.id.editText2);
        sinup=findViewById(R.id.button2);
        progressBar=findViewById(R.id.progressid);

        sinup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Registration();

            }
        });

    }

    private void Registration() {
        String email=emailtext.getText().toString().trim();
        String pass=passtext.getText().toString().trim();

        if(email.isEmpty())
        {
            emailtext.setError("Email is empty");
            emailtext.requestFocus();
            return;
        }
        if(pass.isEmpty())
        {
            passtext.setError("Password is empty");
            passtext.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailtext.setError("Enter a valid email");
            emailtext.requestFocus();
            return;

        }
        mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Sing up is Successfull.Check your email to varify and login.",Toast.LENGTH_LONG).show();
                                Intent i=new Intent(SingUp_activity.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    });
                }


                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"Already Registerd.",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SingUp_activity.this,MainActivity.class));
                        finish();

                    }


                    else
                    {
                        Toast.makeText(getApplicationContext(),"Sing Up Unsuccssfull",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });



    }
}