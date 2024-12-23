package com.example.drivermodule;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class signup extends AppCompatActivity {
    Button signup;
    EditText mail,password,con_pass;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){
            //
        }
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        signup=findViewById(R.id.driversignupbtn);
        mail=findViewById(R.id.driversignupmail);
        password=findViewById(R.id.driversignuppassword);
        con_pass=findViewById(R.id.driverconpassword);
        ProgressDialog progressDialog = new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();


        signup.setOnClickListener(view -> {
            String smailid = mail.getText().toString();
            String spass = password.getText().toString();
            String sconpass = con_pass.getText().toString();
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
            {
                if (!TextUtils.isEmpty(smailid) && !TextUtils.isEmpty(spass)) {

                    if(spass.equals(sconpass)) {
                        progressDialog.setMessage("Registering Please wait");
                        progressDialog.show();
                        Log.d("value=+++++++",smailid+"=="+spass);

                        firebaseAuth.createUserWithEmailAndPassword(smailid, spass).addOnCompleteListener(signup.this, task -> {

                            if (task.isSuccessful()) {

                                Toast.makeText(signup.this, "Account creation is successful", Toast.LENGTH_LONG).show();
                                Objects.requireNonNull(firebaseAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(signup.this, "please check and verify your email", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(signup.this, loginactivity.class);
                                        intent.putExtra("mailid",smailid);
                                        intent.putExtra("password",spass);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(signup.this, "Error in send verify link then retry", Toast.LENGTH_LONG).show();

                                    }


                                });
                            } else {
                                Toast.makeText(signup.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }


                            progressDialog.dismiss();


                        });
                    }
                    else{
                        Toast.makeText(signup.this,"Password and confirm password not match",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(signup.this, "Empty value", Toast.LENGTH_LONG).show();
                }



            }
            else {
                Toast.makeText(signup.this,"Please connect to the Internet",Toast.LENGTH_SHORT).show();
            }


        });


    }
}