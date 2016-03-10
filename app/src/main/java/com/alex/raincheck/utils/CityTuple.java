package com.alex.raincheck.utils;

/**
 * This holds the city information that will be displayed and serialized
 */
public class CityTuple {
    public int cityID;
    public String cityName;
    public String cityCountry;

    public CityTuple(int cityID, String cityName, String cityCountry) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.cityCountry = cityCountry;
    }
}
