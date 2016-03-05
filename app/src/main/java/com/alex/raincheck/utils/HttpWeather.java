package com.alex.raincheck.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * Class used to simplify fetching weather data
 */
public class HttpWeather {
    static final String LOG_TAG = HttpWeather.class.getName();

    private static String CURRENT = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String FORECAST = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static String SUFFIX = "&mode=json&appid=44db6a862fba0b067b1930da0d769e98"; // The API key should really be hidden, but is kept here for the sake of portability/demo-ability

    private String cityName;

    public HttpWeather(String cityName) {
        this.cityName = cityName;
    }

    public String getCurrentWeatherJSON() {
        HttpURLConnection con;

        try {
            con = (HttpURLConnection) ( new URL(CURRENT + cityName + SUFFIX)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
                buffer.append(line + "\r\n");
            con.disconnect();
            br.close();
            return buffer.toString();
        } catch(Exception e) {
            Log.e(LOG_TAG, "Current weather HTTP GET error", e);
            return null;
        }
    }

    public String getForecastJSON() {
        HttpURLConnection con;

        try {
            con = (HttpURLConnection) (new URL(FORECAST + cityName + SUFFIX)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = br.readLine()) != null)
                buffer.append(line + "\r\n");
            con.disconnect();
            br.close();
            return buffer.toString();
        } catch(Exception e) {
            Log.e(LOG_TAG, "Forecast HTTP GET error", e);
            return null;
        }
    }
}
