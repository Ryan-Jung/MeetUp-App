package com.example.darky_000;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
    boolean finishedDialog;

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
            final AlertDialog.Builder buildObj2 = new AlertDialog.Builder(this);
            buildObj2.setMessage("Network is disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog message = buildObj2.create();
            message.show();
        }



        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this, "Location is enabled", Toast.LENGTH_SHORT).show();
        } else {
            final AlertDialog.Builder buildObj = new AlertDialog.Builder(this);
            buildObj.setMessage("Location disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            finishedDialog = true;
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            finishedDialog = true;
                            dialog.cancel();
                        }
                    });
            final AlertDialog message = buildObj.create();
            message.show();
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
                Toast.makeText(this, "Display Events at Location: " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
                controller.sendRequest(latitude, longitude);
            }else{
                Toast.makeText(this, "Default location: San Francisco", Toast.LENGTH_SHORT).show();
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
