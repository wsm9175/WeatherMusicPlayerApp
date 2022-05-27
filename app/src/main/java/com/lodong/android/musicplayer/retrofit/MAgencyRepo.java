package com.lodong.android.musicplayer.retrofit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.lodong.android.musicplayer.model.GeoInfo;
import com.lodong.android.musicplayer.model.ShortWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MAgencyRepo {
    private final String TAG = MAgencyRepo.class.getSimpleName();
    private final static String BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0";
    private final static String SERVICE_KEY = "+W1RIrm0rSgLke2YbG39XOQTIa2Yv/FrkS3Ip2vEUV5Pt+cTyS0nAgzQQSKcPeT1aoiIym8u4XOoLp/1sajHoQ==";
    private static MAgencyRepo instance;
    private Retrofit retrofit;
    private MeteorologicalAgencyAPI maAPI;
    private ShortWeather sw;

    public static MAgencyRepo getInstance(){
        if(instance == null){
            instance = new MAgencyRepo();
        }
        return instance;
    }

    public MutableLiveData<ShortWeather> getWeather(GeoInfo gi){
        //Retrofit 객체 생성
        retrofit = new RetrofitService().getRetroInstance(BASE_URL);

        //인터페이스 객체 생성
        maAPI = retrofit.create(MeteorologicalAgencyAPI.class);

        //API와 통신을 하는 함수 호출
        sw = new ShortWeather();
        MutableLiveData<ShortWeather> data = new MutableLiveData<>();
        callWeatherAPI(data, gi);
        return data;
    }

    private void callWeatherAPI(MutableLiveData<ShortWeather> data, GeoInfo gi){
        int nx = (int) gi.getLat();
        int ny = (int) gi.getLon();

        String baseDate = gi.getCallDate();
        String apiKey = SERVICE_KEY;

        Call<ShortWeather> call = maAPI.getShortWeather(apiKey, 266, 1, "JSON", baseDate, "2300", nx, ny);

        call.enqueue(new Callback<ShortWeather>() {
            @Override
            public void onResponse(Call<ShortWeather> call, Response<ShortWeather> response) {
                if(response.isSuccessful()){
                    data.postValue(response.body());
                    Log.i(TAG, "API CONNECT SUCCESS");
                    Log.i(TAG, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ShortWeather> call, Throwable t) {
                Log.d(TAG, "onFailure " + t.getMessage());
            }
        });
    }
}
