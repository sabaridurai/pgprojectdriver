package com.example.drivermodule;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class home extends AppCompatActivity {
    private long back_pressed;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);
            try
            {
                Objects.requireNonNull(this.getSupportActionBar()).hide();
            }
            catch (NullPointerException e){
                //
            }

            BottomNavigationView navigationView = findViewById(R.id.nav);


            navigationView.setSelectedItemId(R.id.nav_home);
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.viewarea, new firstfragment());
            fragmentTransaction1.commit();

            navigationView.setOnItemSelectedListener(item -> {

                switch (item.getItemId()) {
                    case R.id.nav_home: {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.viewarea, new firstfragment());

                        fragmentTransaction.commit();
                        return true;

                    }
                    case R.id.nav_add: {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.viewarea, new secondfragment());
                        fragmentTransaction.commit();
                        return true;

                    }
                    case R.id.nav_view: {
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.viewarea, new thirdfragment());
                        fragmentTransaction.commit();
                        return true;

                    }

                }
                return false;

            });

        }
        @Override
        public void onBackPressed() {
            BottomNavigationView bottomNavigationView= findViewById(R.id.nav);
            if (back_pressed + 950 > System.currentTimeMillis()){
                super.onBackPressed();
            }
            else{
                Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
                bottomNavigationView.setSelectedItemId(R.id.nav_home);
            }
            back_pressed = System.currentTimeMillis();
        }





    }



