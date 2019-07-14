package com.st18apps.weatherapp.model;

public class City {
    private int id;
    private String name;
    private Coord coord;
    private String country;
    private int timezone;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coord getCoord() {
        return coord;
    }

    public String getCountry() {
        return country;
    }

    public int getTimezone() {
        return timezone;
    }
}
