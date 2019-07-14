package com.st18apps.weatherapp.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.st18apps.weatherapp.model.WeatherData;
import com.st18apps.weatherapp.network.responses.DetailWeatherResponse;
import com.st18apps.weatherapp.repository.Repository;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private Repository repository;

    public ViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
    }

    public LiveData<WeatherData> getCityWeather(String city) {
        repository.loadCityWeather(city);
        return repository.getCityWeather();
    }

    public LiveData<WeatherData> getCityWeather(double lat, double lon) {
        repository.loadCityWeather(lat, lon);
        return repository.getCityWeather();
    }

    public LiveData<List<WeatherData>> getCitiesWeather(String ids) {
        repository.loadCitiesWeather(ids);
        return repository.getCitiesWeather();
    }

    public LiveData<DetailWeatherResponse> getDetailCityWeather(String id) {
        repository.loadDetailCityWeather(id);
        return repository.getDetailCityWeather();
    }

}
