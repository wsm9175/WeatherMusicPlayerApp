package com.lodong.android.musicplayer.util;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class GpsInfo extends Service implements LocationListener {
    private final String TAG = "GpsInfo";
    private Location location;
    private Context context;
    private double latitude;
    private double longitude;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    protected LocationManager locationManager;

    public GpsInfo(Context context) {
        this.context = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.d(TAG, String.valueOf(isGpsEnabled) + String.valueOf(isNetworkEnabled));
            if (!isGpsEnabled && !isNetworkEnabled) {
                Log.d(TAG, "!isGpsEnabled && !isNetworkEnabled");
            } else {
                int hasFineLocationPermission = ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                int hasCoarseLoactionPermission = ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_COARSE_LOCATION);
                Log.d(TAG, "!isGpsEnabled && !isNetworkEnabled else");
                if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                        hasCoarseLoactionPermission == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "hasFineLocationPermission == PackageManager.PERMISSION_DENIED");
                } else
                    return null;

                if (!isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        Log.d(TAG, "location not null");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }

            if (isGpsEnabled) {
                Log.d(TAG, "isGpsEnabled0");
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(TAG, "isGpsEnabled1");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Log.d(TAG, "isGpsEnabled1");
                        if (location != null) {
                            latitude = location.getLatitude();
                            Log.d(TAG, "isGpsEnabled2");
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "" + e.toString());
        }
        return location;
    }

    public double getLatitude() {
        if (location != null) {
            this.latitude = location.getLatitude();
            Log.d(TAG, "latitude nullX : " + String.valueOf(latitude));
        }
        Log.d(TAG, "latitude : " + String.valueOf(latitude));
        return this.latitude;
    }

    public double getLongitude() {
        if (location != null) {
            this.longitude = location.getLongitude();
        }

        return this.longitude;
    }

    public String getAddress(){
        Geocoder geocoder = new Geocoder(context);
        String user_local;
        try {
            // ?????? ????????? ??????????????? Address ????????? ?????? ????????? ??????
            Address address = geocoder.getFromLocation(this.latitude, this.longitude, 3).get(0);

            // Address ???????????? Locality ????????? ??????
            // subLocality??? ?????? ?????? ????????????, ???????????? ?????? ?????? ?????? -> getAdminArea??? ?????? ?????? ??? ??????/ ?????? ?????? ???????????? ?????? -> getLocality??? ?????? ??? ??????
            user_local = address.getLocality();

            // ?????? ????????? ?????????, ?????????, ??????????????? ??? ?????? ???????????? ?????? ??????
            if (user_local == null) {
                user_local = address.getAdminArea();
            }
            // ?????? ????????? ??????????????? Address ????????? ?????? ????????? ??????
            Log.d(TAG, user_local);
        } catch (Exception e) {
            Toast.makeText(context, "????????? ??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            return null;
        }
        return user_local;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
