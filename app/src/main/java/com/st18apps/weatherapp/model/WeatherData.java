package com.st18apps.weatherapp.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import com.st18apps.weatherapp.interfaces.Constants;

import java.util.List;

@Entity(tableName = Constants.DB_NAME)
public class WeatherData {
    @PrimaryKey
    private int id;
    @SerializedName("dt")
    private int dt;
    @SerializedName("dt_txt")
    private String date;
    private String name;

    private String description;
    private String icon;
    private double temp;

    @Ignore
    @SerializedName("main")
    private Main main;
    @Ignore
    private List<Weather> weather = null;
    @Ignore
    private Coord coord;
    @Ignore
    private int cod;
    @Ignore
    private String message;

    public WeatherData(int id, int dt, String date, String name, String description,
                       String icon, double temp) {
        this.id = id;
        this.dt = dt;
        this.date = date;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.temp = temp;
    }

    public String getDescription() {
        if (weather != null) {
            description = weather.get(0).getDescription();
        }
        return description;
    }

    public String getIcon() {
        if (weather != null) {
            icon = weather.get(0).getIcon();
        }
        return icon;
    }

    public double getTemp() {
        if (main != null) {
            temp = main.getTemp();
        }
        return temp;
    }

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

    public String getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return cod != 200;
    }

}
