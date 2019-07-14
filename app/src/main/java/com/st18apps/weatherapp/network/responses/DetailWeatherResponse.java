package com.st18apps.weatherapp.network.responses;

import com.google.gson.annotations.SerializedName;
import com.st18apps.weatherapp.model.City;
import com.st18apps.weatherapp.model.WeatherData;

import java.util.List;

public class DetailWeatherResponse {
    private String cod;
    private double message;
    private int cnt;
    @SerializedName("list")
    private List<WeatherData> weatherDataList = null;
    private City city;

    public String getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public City getCity() {
        return city;
    }

    public boolean isError() {
        return !cod.equals("200");
    }
}
