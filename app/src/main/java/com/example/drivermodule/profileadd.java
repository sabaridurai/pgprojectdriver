package com.example.drivermodule;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class profileadd extends AppCompatActivity {
    private EditText name,licno,dd,mm,yy,address,pcode,dist,state,country,email,Phno;
        private final String regex = "^(([A-Z]{2}[0-9]{2})( )|([A-Z]{2}-[0-9]{2}))((19|20)[0-9][0-9])[0-9]{7}$";

private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileadd);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){
            //
        }
email=findViewById(R.id.email);
        Phno=findViewById(R.id.phonenu);
        Button button = findViewById(R.id.submitbtn);
        name=findViewById(R.id.dname);
        licno=findViewById(R.id.licence);
        dd=findViewById(R.id.date);
        mm=findViewById(R.id.month);
        yy=findViewById(R.id.year);
        address=findViewById(R.id.address);
        pcode=findViewById(R.id.postal);
        dist=findViewById(R.id.dist);
        state=findViewById(R.id.state);
        country=findViewById(R.id.country);
databaseHelper=new DatabaseHelper(this);




        button.setOnClickListener(view -> {
            String phno=Phno.getText().toString();
            String id1 = email.getText().toString();
            String dname=name.getText().toString();
            String lic_no=licno.getText().toString();
            String date=dd.getText().toString();
            String month=mm.getText().toString();
            String year=yy.getText().toString();
            String add=address.getText().toString();
            String district=dist.getText().toString();
            String postcode=pcode.getText().toString();
            String stat=state.getText().toString();
            String count=country.getText().toString();


               Pattern p = Pattern.compile(regex);
               Matcher m = p.matcher(lic_no);
               if (m.matches() && !TextUtils.isEmpty(dname) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(month)
                       && !TextUtils.isEmpty(year) && !TextUtils.isEmpty(add) && !TextUtils.isEmpty(district) && !TextUtils.isEmpty(stat)
                       && !TextUtils.isEmpty(count)&& !TextUtils.isEmpty(postcode) && !TextUtils.isEmpty(id1) && !TextUtils.isEmpty(phno)) {
                   String Dob=date+'-'+month+'-'+year;
                   if(databaseHelper.insert_data(id1,dname,phno,lic_no,Dob,add,postcode,district,stat,count))
                   {

                       Toast.makeText(profileadd.this, "added successsfully", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(profileadd.this, home.class);

                       startActivity(intent);
                       finish();
                   }
                   else {
                       Toast.makeText(profileadd.this, "Did not write data", Toast.LENGTH_SHORT).show();
                   }
               }
               else if (!m.matches()) {
                   Toast.makeText(profileadd.this, "Invalid Licence", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(profileadd.this, "Empty Value", Toast.LENGTH_SHORT).show();
               }

        });


    }
}