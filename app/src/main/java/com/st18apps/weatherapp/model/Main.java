package com.st18apps.weatherapp.model;

import com.google.gson.annotations.SerializedName;

public class Main {
    private double temp;
    private double pressure;
    private double humidity;
    @SerializedName("temp_min")
    private double tempMin;
    @SerializedName("temp_max")
    private double tempMax;

    public double getTemp() {
        return temp;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }
}
