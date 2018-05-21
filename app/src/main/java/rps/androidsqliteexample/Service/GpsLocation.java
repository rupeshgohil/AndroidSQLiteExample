package rps.androidsqliteexample.Service;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsLocation extends Service implements LocationListener {

    // saving the context for later use
    private final Context mContext;

    // if GPS is enabled
    boolean isGPSEnabled = false;
    // if Network is enabled
    boolean isNetworkEnabled = false;
    // if Location co-ordinates are available using GPS or Network
    public boolean isLocationAvailable = false;

    // Location and co-ordinates coordinates
    Location mLocation;
    double mLatitude;
    double mLongitude;

    // Minimum time fluctuation for next update (in milliseconds)
    private static final long TIME = 30000;
    // Minimum distance fluctuation for next update (in meters)
    private static final long DISTANCE = 20;

    // Declaring a Location Manager
    protected LocationManager mLocationManager;

    public GpsLocation(Context context) {
        this.mContext = context;
        mLocationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

    }
    public Location getLocation() {
        try {
            isGPSEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (isGPSEnabled) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                }
                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, TIME, DISTANCE, this);
                if (mLocationManager != null) {
                    mLocation = mLocationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (mLocation != null) {
                        mLatitude = mLocation.getLatitude();
                        mLongitude = mLocation.getLongitude();
                        isLocationAvailable = true; // setting a flag that
                         return mLocation;
                    }
                }
            }
            isNetworkEnabled = mLocationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isNetworkEnabled) {
                mLocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, TIME, DISTANCE, this);
                if (mLocationManager != null) {
                    mLocation = mLocationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (mLocation != null) {
                        mLatitude = mLocation.getLatitude();
                        mLongitude = mLocation.getLongitude();
                        isLocationAvailable = true; // setting a flag that
                        // location is available
                        return mLocation;
                    }
                }
            }

            if (!isGPSEnabled) {
                // so asking user to open GPS
                askUserToOpenGPS();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // if reaching here means, location was not available, so setting the
        // flag as false
        isLocationAvailable = false;
        return null;
    }
    public String getLocationAddress() {

        if (isLocationAvailable) {

            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(mLatitude, mLongitude, 1);
            } catch (IOException e1) {
                e1.printStackTrace();
                return ("IO Exception trying to get address:" + e1);
            } catch (IllegalArgumentException e2) {
                // Error message to post in the log
                String errorString = "Illegal arguments "
                        + Double.toString(mLatitude) + " , "
                        + Double.toString(mLongitude)
                        + " passed to address service";
                e2.printStackTrace();
                return errorString;
            }
            if (addresses != null && addresses.size() > 0) {
                  /* Address address = addresses.get(0);
                    String addressText = String.format(
                        "%s, %s, %s",
                        // If there's a street address, add it
                        address.getMaxAddressLineIndex() > 0 ? address
                                .getAddressLine(0) : "",
                         address.getLocality(),
                        address.getCountryName());*/
                String cityName = addresses.get(0).getAddressLine(0);

                Log.e("FullAddress",cityName);
                // Return the text
                return cityName;
            } else {
                return "No address found by the service: Note to the developers, If no address is found by google itself, there is nothing you can do about it.";
            }
        } else {
            return "Location Not available";
        }

    }
    public double getLatitude() {
        if (mLocation != null) {
            mLatitude = mLocation.getLatitude();
        }
        return mLatitude;
    }
    public double getLongitude() {
        if (mLocation != null) {
            mLongitude = mLocation.getLongitude();
        }
        return mLongitude;
    }
    public void closeGPS() {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
        }
    }
    public void askUserToOpenGPS() {
        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle("Location not available, Open GPS?")
                .setMessage("Activate GPS to use use location services?")
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
    @Override
    public void onLocationChanged(Location location) {
        mLatitude = location.getLatitude();
        mLongitude = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
