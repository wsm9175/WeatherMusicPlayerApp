package com.lodong.android.musicplayer.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.lodong.android.musicplayer.R;
import com.lodong.android.musicplayer.model.GeoInfo;
import com.lodong.android.musicplayer.model.ShortWeather;
import com.lodong.android.musicplayer.util.PermissionCheck;
import com.lodong.android.musicplayer.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();
    private MainViewModel mainViewModel;
    private ShortWeather sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
        mainViewModel.setParentContext(this);
        //permission check
        requestPermissions();

        //위치정보를 가져옴
        mainViewModel.getLocation();
        observeAPI();
    }


    private boolean requestPermissions(){
        return PermissionCheck.checkAndRequestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION});
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int result = PermissionCheck.onRequestPermissionsResult(this, requestCode, permissions, grantResults);

        if (result == PermissionCheck.RESULT_GRANTED) {

        }else if(result == PermissionCheck.RESULT_NOT_GRANTED){
            showDialogOK(getString(R.string.permission_not_allow),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    requestPermissions();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    finish();
                                    break;
                            }
                        }
                    });
        }else if(result == PermissionCheck.RESULT_DENIED){
            Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private void observeGPS(){
        mainViewModel.getGeo().observe(this, new Observer<GeoInfo>() {
            @Override
            public void onChanged(GeoInfo geoInfo) {
                mainViewModel.callApi(mainViewModel.getGeo().getValue());
            }
        });
    }

    private void observeAPI(){
        mainViewModel.getWeather().observe(this, new Observer<ShortWeather>() {
            @Override
            public void onChanged(ShortWeather shortWeather) {
                sw = mainViewModel.getWeather().getValue();
                Log.i(TAG, sw.toString());

            }
        });
    }


}