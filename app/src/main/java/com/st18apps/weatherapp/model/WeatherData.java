package com.st18apps.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherData {
    private Coord coord;
    private List<Weather> weather = null;
    @SerializedName("main")
    private Main main;
    @SerializedName("dt")
    private int dt;
    private int id;
    private String name;
    private int cod;
    private String message;

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public int getDt() {
        return dt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return cod != 200;
    }

}
