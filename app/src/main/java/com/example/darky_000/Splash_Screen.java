package com.example.darky_000;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.darky_000.app.App;
import com.example.darky_000.controller.JsonController;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

/**
 * Created by darky_000 on 11/24/2016.
 */

public class Splash_Screen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    String latitude;
    String longitude;
    JsonController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        StoryEvent.get();

        controller = new JsonController(
                new JsonController.OnResponseListener() {
                    @Override
                    public void onSuccess(List<StoryEvent> storyEvents) {
                        Intent intent = new Intent(Splash_Screen.this, StoryListActivity.class);

                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Log.e("onFailure", errorMessage);
                    }
                }
        );


        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cManager.getActiveNetworkInfo();
        if(nInfo!=null && nInfo.isConnected()){
            Toast.makeText(this, "Network is available", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Please turn on Wi-Fi and restart the app to find events ", Toast.LENGTH_LONG).show();
        }



        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "Location is enabled", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please turn on Location and restart the app to find events close to you", Toast.LENGTH_LONG).show();
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }


    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();

        // for physical device
        // getLocation();

        // for emulator
        //   getLocation("37.7216269", "-122.4766322");
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        getLocation();
    }

    private void getLocation() {
        if (ContextCompat.checkSelfPermission(App.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                latitude = String.valueOf(mLastLocation.getLatitude());
                longitude = String.valueOf(mLastLocation.getLongitude());
                Log.i("mylatitude", latitude);
                Log.i("mylatitude", longitude);
                Toast.makeText(this, "Displaying Events at Location: " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
                controller.sendRequest(latitude, longitude);
            }else{
                Toast.makeText(this, "Could not find location. Default location: San Francisco", Toast.LENGTH_SHORT).show();
                getLocation("37.7216269", "-122.4766322");
            }
        }
    }


    private void getLocation(String latitude, String longitude) {

        controller.sendRequest(latitude, longitude);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}
