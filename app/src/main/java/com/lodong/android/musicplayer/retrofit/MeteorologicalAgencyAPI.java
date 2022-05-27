package com.lodong.android.musicplayer.retrofit;

import com.lodong.android.musicplayer.model.ShortWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MeteorologicalAgencyAPI {
    @GET("getVilageFcst")
    Call<ShortWeather> getShortWeather(@Query("serviceKey") String serviceKey, @Query("numOfRows") int numOfRows, @Query("pageNo") int pageNo,
                                       @Query("dataType") String dataType, @Query("base_date") String base_date,
                                       @Query("base_time") String base_time, @Query("nx") int nx, @Query("ny") int ny);


}
