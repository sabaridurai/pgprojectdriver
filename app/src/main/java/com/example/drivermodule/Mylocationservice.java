package com.example.drivermodule;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Mylocationservice extends Service implements  .
        {
    DatabaseHelperowner databaseHelperowner;
    FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    GoogleApiClient mGoogleApiClient;
    private String owner_id;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    public Map<String, String> map = new HashMap<>();
private final String ch_id="Programming Line";
private final int id_ch=1;


@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (isLocationEnabled()) {
//            Toast.makeText(Mylocationservice.this, "Sharing...", Toast.LENGTH_LONG).show();
//            buildGoogleApiClient();
//        } else {
//            Toast.makeText(Mylocationservice.this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//            Intent intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent1);
//        }
//        return START_STICKY;
//    }
    @Override
    public void onCreate() {
        databaseHelperowner = new DatabaseHelperowner(Mylocationservice.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseHelperowner = new DatabaseHelperowner(Mylocationservice.this);
        if (isLocationEnabled()) {
            Toast.makeText(Mylocationservice.this, "Sharing...", Toast.LENGTH_LONG).show();
            buildGoogleApiClient();//call
        } else {
            Toast.makeText(Mylocationservice.this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        NotificationChannelfun();
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),ch_id);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground));
        builder.setContentTitle("Location Sharing");
        builder.setContentText("Locaton Shared to your subscribers");
        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(id_ch,builder.build());
        super.onCreate();
    }

    private void NotificationChannelfun() {


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description="Include all simple notifications";
            CharSequence charSequence="Simple Notification";
            int imp= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel=new NotificationChannel(ch_id,charSequence,imp);
            notificationChannel.setDescription(description);


            NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if(notificationManager!=null)
            {
                notificationManager.createNotificationChannel(notificationChannel);

            }

        }
    }


    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) Mylocationservice.this.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }




    @Override
    public void onDestroy() {

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();

            Toast.makeText(Mylocationservice.this,"Stopping",Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(Mylocationservice.this,"Stopped",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }




    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(Mylocationservice.this,"Connection Error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(Mylocationservice.this,"Connection Failed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mLastLocation = location;
        if (location == null) {
            Toast.makeText(Mylocationservice.this,"Did not get Location on your device",Toast.LENGTH_SHORT).show();
        }
        else {

            try {//try to get owner id
                Cursor cursor = databaseHelperowner.getownerid();
                if (cursor.getCount() != 0) {

                    while (cursor.moveToNext()) {
                        owner_id = cursor.getString(0);


                    }
                }
                cursor.close();
            }
            catch (NullPointerException e) {
                Log.d("Nullpointer exception+", String.valueOf(e));
            }


            map.put("latitude", String.valueOf(location.getLatitude()));
            map.put("longitude", String.valueOf(location.getLongitude()));
            DatabaseReference reference = firebaseDatabase.getReference("owner");

            //data add to fire base:

            reference.child(owner_id).child("drivers").child(Objects.requireNonNull(firebaseAuth.getUid())).child("location").setValue(map).addOnCompleteListener(task1 -> {
                if (task1.isSuccessful()) {
                    Toast.makeText(Mylocationservice.this, "Live location", Toast.LENGTH_SHORT).show();}
                else{
                    Toast.makeText(Mylocationservice.this, "Firebse error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}