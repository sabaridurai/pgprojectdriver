package com.example.drivermodule;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;


public class firstfragment extends Fragment {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
Button button1,button2,button3;
FirebaseAuth firebaseAuth;
    GoogleApiClient mGoogleApiClient;
ImageButton imagebtn;
    public firstfragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_firstfragment, container, false);
        firebaseAuth=FirebaseAuth.getInstance();
button1=view.findViewById(R.id.sharebtn);
button2=view.findViewById(R.id.stopbtn);
button3=view.findViewById(R.id.mapbtn);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if ((ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)&&
                    (ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {


            }
            else {
                ActivityCompat.requestPermissions(requireActivity(),new String[] { Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
                        },
                        MY_PERMISSIONS_REQUEST_LOCATION);

            }

        }


        if (isLocationEnabled()) {




                button1.setOnClickListener(view1 -> {
                  button1.setBackgroundResource(R.drawable.btnbgtwo);
                  button2.setBackgroundResource(R.drawable.btnbg);
                    Intent intent = new Intent(requireActivity(), Mylocationservice.class);
                    requireActivity().startService(intent);
                });


                button2.setOnClickListener(view12 -> {
                    button2.setBackgroundResource(R.drawable.btnbgtwo);
                    button1.setBackgroundResource(R.drawable.btnbg);

                    Intent intent1 = new Intent(requireActivity(), Mylocationservice.class);
                    requireActivity().stopService(intent1);


                });

            button3.setOnClickListener(view13 -> {
                Intent intent=new Intent(getActivity(),MapsActivity.class);
                startActivity(intent);
            });

        }
        else {
            Toast.makeText(requireActivity(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }





        return view;
    }
    //move to location setting
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}