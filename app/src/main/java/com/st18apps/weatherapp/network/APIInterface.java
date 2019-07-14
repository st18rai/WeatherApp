package com.st18apps.weatherapp.network;

import com.st18apps.weatherapp.model.WeatherData;
import com.st18apps.weatherapp.network.responses.DetailWeatherResponse;
import com.st18apps.weatherapp.network.responses.WeatherListResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("weather")
    Observable<WeatherData> getCityWeatherByName(@Query("q") String city,
                                                 @Query("units") String units,
                                                 @Query("lang") String lang,
                                                 @Query("appid") String appID);

    @GET("weather")
    Observable<WeatherData> getCityWeatherByCoordinates(@Query("lat") double lat,
                                                        @Query("lon") double lon,
                                                        @Query("units") String units,
                                                        @Query("lang") String lang,
                                                        @Query("appid") String appID);

    @GET("group")
    Observable<WeatherListResponse> getCitiesWeather(@Query("id") String ids,
                                                     @Query("units") String units,
                                                     @Query("lang") String lang,
                                                     @Query("appid") String appID);

    @GET("forecast")
    Observable<DetailWeatherResponse> getDetailWeather(@Query("id") String ids,
                                                       @Query("units") String units,
                                                       @Query("lang") String lang,
                                                       @Query("appid") String appID);

}
