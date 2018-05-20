package rps.androidsqliteexample;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import rps.androidsqliteexample.Database.LocalDataBase;
import rps.androidsqliteexample.Service.GpsLocation;
import rps.androidsqliteexample.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    public static ActivityMainBinding mainBinding;
    LocalDataBase db;
    String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        db = new LocalDataBase(this);
        GpsLocation mGPSService = new GpsLocation(this);
        mGPSService.getLocation();
        if (mGPSService.isLocationAvailable == false) {
            Toast.makeText(getApplicationContext(), "Your location is not available, please try again.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            double latitude = mGPSService.getLatitude();
            double longitude = mGPSService.getLongitude();
           // Toast.makeText(getApplicationContext(), "Latitude:" + latitude + " | Longitude: " + longitude, Toast.LENGTH_LONG).show();
            address = mGPSService.getLocationAddress();
            mainBinding.txtLocation.setText(address);

        }

        Toast.makeText(getApplicationContext(), "Your address is: " + address, Toast.LENGTH_SHORT).show();

        // make sure you close the gps after using it. Save user's battery power
        mGPSService.closeGPS();
        mainBinding.txtSugnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ActivitySignup.class);
                startActivity(i);
            }
        });
        mainBinding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i = new Intent(MainActivity.this,ActivityView.class);
                //startActivity(i);
               if(isValid("MainActivity")){
                   if(db.checkUserNameLogin(mainBinding.edmainusername.getText().toString().trim())){
                       if(db.isLogin(mainBinding.edmainusername.getText().toString().trim(),
                               mainBinding.edmainPassword.getText().toString().trim())){
                           Intent i = new Intent(MainActivity.this,ActivityView.class);
                           startActivity(i);
                       }else{
                           Toast.makeText(MainActivity.this, R.string.loginfalis, Toast.LENGTH_SHORT).show();
                       }
                   }else{
                       Toast.makeText(MainActivity.this, R.string.vluser, Toast.LENGTH_SHORT).show();
                   }

                }
            }
        });

    }
   }


