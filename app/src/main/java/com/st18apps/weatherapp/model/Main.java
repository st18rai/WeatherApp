package com.st18apps.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Main {
    private double temp;
    private int pressure;
    private int humidity;
    @SerializedName("temp_min")
    private int tempMin;
    @SerializedName("temp_max")
    private int tempMax;

    public double getTemp() {
        return temp;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getTempMin() {
        return tempMin;
    }

    public int getTempMax() {
        return tempMax;
    }
}
