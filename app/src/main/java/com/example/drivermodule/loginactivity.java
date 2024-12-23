package com.example.drivermodule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginactivity extends AppCompatActivity {
FirebaseAuth firebaseAuth;
    EditText drivermail, driverpassword;
    Button button;
    String id, pass;
    ConnectivityManager connectivityManager;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){
            //
        }
        firebaseAuth=FirebaseAuth.getInstance();
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
button=findViewById(R.id.driverloginbtn);
drivermail=findViewById(R.id.drivermail);
driverpassword=findViewById(R.id.driverpassword);
textView=findViewById(R.id.signuptext);


ProgressDialog progressDialog = new ProgressDialog(loginactivity.this);




button.setOnClickListener(view -> {
    id = drivermail.getText().toString();
    pass = driverpassword.getText().toString();

    if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(pass)) {
            progressDialog.setMessage("Login..");
            progressDialog.show();


            Task<AuthResult> authResultTask = firebaseAuth.signInWithEmailAndPassword(id, pass).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {

                    if (firebaseAuth.getCurrentUser() != null) {

                        Intent intent = new Intent(loginactivity.this, home.class);
                        startActivity(intent);
                        finish();
                    } else {

                        Toast.makeText(loginactivity.this, "Did not login properly", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(loginactivity.this, id + " Not a admin", Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();
            });

        } else {
            Toast.makeText(loginactivity.this, "Empty Value", Toast.LENGTH_SHORT).show();
        }


    } else {
        Toast.makeText(loginactivity.this, "Please connet to the Internet", Toast.LENGTH_SHORT).show();
    }
});
    }


}