package com.st18apps.weatherapp.network;

import com.st18apps.weatherapp.model.WeatherData;
import com.st18apps.weatherapp.network.responses.BaseListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("weather")
    Observable<WeatherData> getCityWeather(@Query("q") String city,
                                           @Query("units") String units,
                                           @Query("lang") String lang,
                                           @Query("appid") String appID);

    @GET("group")
    Observable<BaseListResponse> getCitiesWeather(@Query("id") String ids,
                                                  @Query("units") String units,
                                                  @Query("lang") String lang,
                                                  @Query("appid") String appID);

}
