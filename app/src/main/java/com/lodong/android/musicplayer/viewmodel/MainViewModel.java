package com.lodong.android.musicplayer.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lodong.android.musicplayer.model.GeoInfo;
import com.lodong.android.musicplayer.model.ShortWeather;
import com.lodong.android.musicplayer.retrofit.MAgencyRepo;
import com.lodong.android.musicplayer.util.GpsInfo;
import com.lodong.android.musicplayer.util.GpsTransfer;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainViewModel extends ViewModel {
    private final String TAG = MainViewModel.class.getSimpleName();
    private WeakReference<Activity> mActivityRef;

    private GpsInfo gpsInfo;
    private GpsTransfer gpsTransfer;
    private GeoInfo geoInfo;

    private MutableLiveData<ShortWeather> sw = new MutableLiveData<>();
    private MutableLiveData<GeoInfo> mldGi = new MutableLiveData<>();
    private MAgencyRepo maRepo;

    public void setParentContext(Activity parentContext) {
        mActivityRef = new WeakReference<>(parentContext);
        gpsInfo = new GpsInfo(mActivityRef.get());
    }

    public void getLocation() {
        Log.d(TAG, "getLocation");

        gpsTransfer = new GpsTransfer();
        double longitude = gpsInfo.getLongitude();
        double latitude = gpsInfo.getLatitude();

        Log.d(TAG, "user location =" + longitude + " " + latitude);

        gpsTransfer.setLon(longitude);
        gpsTransfer.setLat(latitude);

        gpsTransfer.transfer(gpsTransfer, 0);
        Log.d(TAG, gpsTransfer.toString());

        geoInfo = new GeoInfo();
        geoInfo.setLat(gpsTransfer.getLat());
        geoInfo.setLon(gpsTransfer.getLon());
        getTime();
    }

    public void getTime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timeSdf = new SimpleDateFormat("HH");

        Calendar cal = Calendar.getInstance();

        geoInfo.setNowDate(dateSdf.format(cal.getTime()));
        geoInfo.setNowTime(timeSdf.format(cal.getTime()));

        cal.add(Calendar.DATE, -1);
        geoInfo.setCallDate(dateSdf.format(cal.getTime()));

        Log.d(TAG, "DATE : " + geoInfo.getNowDate());
        Log.d(TAG, "TIME : " + geoInfo.getNowTime());
        Log.d(TAG, "CALL DATE : " + geoInfo.getCallDate());

        mldGi.setValue(geoInfo);
    }

    public void callApi(GeoInfo geoInfo){
        if(sw!=null){
            return;
        }

        maRepo = MAgencyRepo.getInstance();
        sw = maRepo.getWeather(geoInfo);
        Log.i(TAG, "API Connection finish");
    }

    public LiveData<ShortWeather> getWeather(){
        return sw;
    }

    public LiveData<GeoInfo> getGeo(){
        return mldGi;
    }

}
