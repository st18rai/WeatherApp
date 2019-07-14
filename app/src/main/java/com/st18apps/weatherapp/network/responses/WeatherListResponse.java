package com.st18apps.weatherapp.network.responses;

import com.google.gson.annotations.SerializedName;
import com.st18apps.weatherapp.model.WeatherData;

import java.util.List;

public class WeatherListResponse {
    @SerializedName("cnt")
    private int count;
    @SerializedName("list")
    private List<WeatherData> weatherDataList = null;
    private int cod;
    private String message;

    public int getCount() {
        return count;
    }

    public List<WeatherData> getWeatherDataList() {
        return weatherDataList;
    }

    public int getCod() {
        return cod;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return cod != 0;
    }
}
