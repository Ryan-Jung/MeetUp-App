package com.example.darky_000;


/**
 * Created by Jesse on 12/6/2016.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.darky_000.app.App;

//Use

public class GeoLocator {

//    TextView textLat;
//    TextView textLong;

    private String lat;
    private String lon;

    //    @Override
    GeoLocator() {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.geolocation);
//
//        textLat = (TextView) findViewById(R.id.textLat);
//        textLong = (TextView) findViewById(R.id.textLong);

        LocationManager lm = (LocationManager) App.getContext().getSystemService(Context.LOCATION_SERVICE);
        LocationListener ll = new mylocationlistener();
        if (ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(App.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double pLong = location.getLongitude();
                double pLat = location.getLatitude();

//                 textLat.setText(Double.toString(pLat));
//                 textLong.setText(Double.toString(pLong));

                setLat(Double.toString(pLat));
                setLon(Double.toString(pLong));

                Log.i("latlon", pLat + " " + pLong);
            }

        }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);

    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

     class mylocationlistener implements LocationListener {

         @Override
         public void onLocationChanged(Location location) {
             if (location != null) {
                 double pLong = location.getLongitude();
                 double pLat = location.getLatitude();

//                 textLat.setText(Double.toString(pLat));
//                 textLong.setText(Double.toString(pLong));

                 setLat(Double.toString(pLat));
                 setLon(Double.toString(pLong));

                 Log.i("latlon", pLat + " " + pLong);
             }
         }

         @Override
                 public void onProviderDisabled (String provider){
                 }


         @Override
         public void onStatusChanged(String s, int i, Bundle bundle) {

         }

                 @Override
                 public void onProviderEnabled (String provider){

                 }

             }
         }


/* This is the xml textview code needed to display the long and lat in the layout
<TextView
        android:text="Longitude"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/imageView"
        android:layout_toRightOf="@+id/imageView"
        android:layout_toEndOf="@+id/imageView"
        android:layout_marginTop="65dp"
        android:id="@+id/textView2" />

    <TextView
        android:text=""
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="15dp"
        android:layout_marginStart="15dp"
        android:id="@+id/textLong"
        android:layout_alignBaseline="@+id/textView2"
        android:layout_alignBottom="@+id/textView2"
        android:layout_toRightOf="@+id/textView2"
        android:layout_toEndOf="@+id/textView2" />

    <TextView
        android:text="Latitude"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="17dp"
        android:id="@+id/textView4"
        android:layout_alignParentTop="true"
        android:layout_toRightOf="@+id/imageView"
        android:layout_toEndOf="@+id/imageView" />

    <TextView
        android:text=""
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:id="@+id/textLat"
        android:layout_alignBaseline="@+id/textView4"
        android:layout_alignBottom="@+id/textView4"
        android:layout_toRightOf="@+id/textLong"
        android:layout_toEndOf="@+id/textLong" />


 */