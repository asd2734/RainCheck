package com.alex.raincheck.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.raincheck.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by alex on 3/6/16.
 */
public class GetCitiesTask extends AsyncTask<String, Void, String> {
    static final String LOG_TAG = GetCitiesTask.class.getName();

    private static String PREFIX = "http://api.openweathermap.org/data/2.5/find?q=";
    private static String SUFFIX = "&type=like&mode=json&appid=44db6a862fba0b067b1930da0d769e98"; // The API key should really be hidden, but is kept here for the sake of portability/demo-ability

    private String cityName;
    private WeakReference< ListView > listViewReference;
    private WeakReference< Context > contextReference;

    public GetCitiesTask(Context context, ListView listView) {
        super();
        this.contextReference = new WeakReference< Context >(context);
        this.listViewReference = new WeakReference< ListView >(listView);
    }

    public String getCitiesJSON() {
        HttpURLConnection con;

        // Refreshes up to 10 times on response error
        for (int refreshCount = 0; refreshCount < 10; refreshCount++) {
            try {
                con = (HttpURLConnection) (new URL(PREFIX + cityName + SUFFIX)).openConnection();
                con.setRequestMethod("GET");
                con.setDoInput(true);
                con.setDoOutput(false);
                con.connect();

                StringBuffer buffer = new StringBuffer();
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = br.readLine()) != null)
                    buffer.append(line + "\r\n");
                con.disconnect();
                br.close();
                return buffer.toString();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Current weather HTTP GET error", e);
            }
        }

        return null;
    }

    @Override
    protected String doInBackground(String... cityParam) {
        this.cityName = cityParam[0];
        return getCitiesJSON();
    }

    @Override
    protected void onPostExecute(String responseJSON) {
        if (listViewReference != null && contextReference != null) {
            try {
                Context context = contextReference.get();
                ListView listView = listViewReference.get();
                JSONObject cityWeatherJSON = new JSONObject(responseJSON);
                // Temperature and humidity data are under "main"
                JSONArray cityJSONList = cityWeatherJSON.getJSONArray("list");

                ArrayList<CityNameIDPair> cityNameIDPairs = new ArrayList<CityNameIDPair>();

                for (int i = 0; i < cityJSONList.length(); i++) {
                    JSONObject cityObject = cityJSONList.getJSONObject(i);
                    CityNameIDPair pair = new CityNameIDPair(cityObject.getInt("id"), cityObject.getString("name"));
                    cityNameIDPairs.add(pair);
                }

                listView.setAdapter(new SearchListAdapter(context, cityNameIDPairs));
            } catch (Exception e) {
                Log.e(LOG_TAG, "JSON parse error", e);
            }
        }
    }
}
