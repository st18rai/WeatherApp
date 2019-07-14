package com.st18apps.weatherapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.st18apps.weatherapp.model.WeatherData;
import com.st18apps.weatherapp.network.ApiClient;
import com.st18apps.weatherapp.network.responses.DetailWeatherResponse;
import com.st18apps.weatherapp.utils.RxUtil;

import java.util.List;

public class Repository {
    private final String UNITS = "metric";
    private final String LANG = "ru";

    private final MutableLiveData<WeatherData> cityWeather = new MutableLiveData<>();
    private final MutableLiveData<DetailWeatherResponse> detailCityWeather = new MutableLiveData<>();
    private final MutableLiveData<List<WeatherData>> citiesWeather = new MutableLiveData<>();

    public Repository(Application application) {
    }

    // for one city
    public LiveData<WeatherData> getCityWeather() {
        return cityWeather;
    }
    private void setCityWeather(WeatherData data) {
        cityWeather.setValue(data);
    }

    // for several cities
    public LiveData<List<WeatherData>> getCitiesWeather() {
        return citiesWeather;
    }
    private void setCitiesWeather(List<WeatherData> dataList) {
        citiesWeather.setValue(dataList);
    }

    // detail weather
    public LiveData<DetailWeatherResponse> getDetailCityWeather() {
        return detailCityWeather;
    }
    private void setDetailCityWeather(DetailWeatherResponse data) {
        detailCityWeather.setValue(data);
    }

    public void loadCityWeather(String city) {

        RxUtil.networkConsumer(ApiClient.getApiInterface().getCityWeather(city,
                UNITS, LANG, ApiClient.APP_ID), weatherData -> {

            if (!weatherData.isError()) {
                setCityWeather(weatherData);
            }

        }, throwable -> {
            setCityWeather(null);
            throwable.printStackTrace();
        });
    }

    public void loadCitiesWeather(String citiesId) {

        RxUtil.networkConsumer(ApiClient.getApiInterface().getCitiesWeather(citiesId,
                UNITS, LANG, ApiClient.APP_ID), baseListResponse -> {

            if (!baseListResponse.isError()) {
                setCitiesWeather(baseListResponse.getWeatherDataList());
            }

        }, Throwable::printStackTrace);
    }

    public void loadDetailCityWeather(String cityId) {

        RxUtil.networkConsumer(ApiClient.getApiInterface().getDetailWeather(cityId,
                UNITS, LANG, ApiClient.APP_ID), detailWeatherResponse -> {

            if (!detailWeatherResponse.isError()) {
                setDetailCityWeather(detailWeatherResponse);
            }

        }, Throwable::printStackTrace);

    }
}
