package com.st18apps.weatherapp.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.st18apps.weatherapp.model.WeatherData;

import java.util.List;

@Dao
public interface CitiesWeatherDao {
    @Insert
    void insert(WeatherData weatherData);

    @Update
    void update(WeatherData weatherData);

    @Delete
    void delete(WeatherData weatherData);

    @Query("SELECT * FROM cities_weather")
    LiveData<List<WeatherData>> getAllWeatherData();
}
