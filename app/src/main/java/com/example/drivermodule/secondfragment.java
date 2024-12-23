package com.example.drivermodule;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class secondfragment extends Fragment {
     ImageButton imageButton1,imageButton2,imageButton3,imageButton4;
  FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
private DatabaseHelper databaseHelper;
    private DatabaseHelperowner databaseHelperowner;
    public String val;
private int check=0;
public Map<String, String> mqp1 = new HashMap<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_secondfragment, container, false);

imageButton1=view.findViewById(R.id.img1);
imageButton2=view.findViewById(R.id.deleteimg);
imageButton3=view.findViewById(R.id.img3);
imageButton4=view.findViewById(R.id.updateimg);
        databaseHelper=new DatabaseHelper(getContext());
        databaseHelperowner=new DatabaseHelperowner(getContext());


imageButton3.setOnClickListener(view14 -> {
    Intent intent=new Intent(getActivity(),profileadd.class);
    startActivity(intent);

});
imageButton1.setOnClickListener(view1 -> {

    Cursor cursor=databaseHelper.getAlldata();
    if(cursor.getCount()!=0)
    {
        while(cursor.moveToNext()){
          mqp1.put("Email",cursor.getString(0));
            mqp1.put("Name",cursor.getString(1));
            mqp1.put("Phno",cursor.getString(2));
            mqp1.put("LicenceNO",cursor.getString(3));
            mqp1.put("DOB",cursor.getString(4));
           mqp1.put("State",cursor.getString(8));
            mqp1.put("Country",cursor.getString(9));
            mqp1.put("Address",cursor.getString(5));
            mqp1.put("Postal_code",cursor.getString(6));
            mqp1.put("District",cursor.getString(7));


        }

        cursor.close();
        scancode();
    }
    else {
        Toast.makeText(getActivity(), "Please Add your profile details", Toast.LENGTH_SHORT).show();



    }


});
imageButton2.setOnClickListener(view12 -> {
   boolean res=databaseHelper.delete();
   if(res){
       Toast.makeText(getActivity(),"Deleted successfully",Toast.LENGTH_SHORT).show();
   }
   else {

       Toast.makeText(getActivity(),"No data found",Toast.LENGTH_SHORT).show();
   }


});
imageButton4.setOnClickListener(view13 -> {
    //update
});

        return view;
    }



    private void scancode() {
        ScanOptions options=new ScanOptions();
        options.setPrompt("volume up turn on flash");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }
    ActivityResultLauncher<ScanOptions> barLauncher=registerForActivityResult(new ScanContract(), result -> {
        val=result.getContents();

        if(result.getContents()!=null && val.length()==28 ){

            //locaal database

           try {
               if (databaseHelperowner.insertownerdata(val)) {
                   check = 1;
               } else if (databaseHelperowner.update(val)) {
                   check = 1;
               } else {
                   Toast.makeText(getActivity(), "Local database error", Toast.LENGTH_SHORT).show();
                   check = 0;
               }
           }
           catch (Exception e){
               //Log.d(String.valueOf(e.getMessage()));
           }




            firebaseDatabase=FirebaseDatabase.getInstance();
            firebaseAuth=FirebaseAuth.getInstance();

            DatabaseReference reference=firebaseDatabase.getReference("owner");
            //data add to fire base:
            reference.child(result.getContents()).child("drivers").child(Objects.requireNonNull(firebaseAuth.getUid())).setValue(mqp1).addOnCompleteListener(task -> {
           if (task.isSuccessful() & check>0){
               Toast.makeText(getActivity(),"Added successfully",Toast.LENGTH_SHORT).show();

           }
           else {
               Toast.makeText(getActivity(),"Network Error",Toast.LENGTH_SHORT).show();
           }
            });
        }
        else {
            Toast.makeText(getActivity(),"QR code miss match",Toast.LENGTH_SHORT).show();
        }
    });


}