package com.alex.raincheck.utils;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.raincheck.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/*
 * Class used to simplify fetching weather data
 */
public class GetCurrentWeatherTask extends AsyncTask<String, Void, String>{
    static final String LOG_TAG = GetCurrentWeatherTask.class.getName();

    private static String PREFIX = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String FORECAST = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private static String SUFFIX = "&mode=json&appid=44db6a862fba0b067b1930da0d769e98"; // The API key should really be hidden, but is kept here for the sake of portability/demo-ability

    private WeakReference< View > viewReference;
    private String cityName;

    public GetCurrentWeatherTask(View view) {
        super();
        this.viewReference = new WeakReference< View >(view);
    }

    public String getCurrentWeatherJSON() {
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
        return getCurrentWeatherJSON();
    }

    @Override
    protected void onPostExecute(String responseJSON) {
        if (viewReference != null && responseJSON != null) {
            try {
                View view = viewReference.get();
                JSONObject cityWeatherJSON = new JSONObject(responseJSON);
                // Temperature and humidity data are under "main"
                JSONObject mainJSON = cityWeatherJSON.getJSONObject("main");
                JSONObject weatherJSON = cityWeatherJSON.getJSONArray("weather").getJSONObject(0);
                // Temperatures in JSON are in Kelvins
                int temp = (int) (Double.parseDouble(mainJSON.getString("temp")) - 273.15);
                int tempHigh = (int) (Double.parseDouble(mainJSON.getString("temp_max")) - 273.15);
                int tempLow = (int) (Double.parseDouble(mainJSON.getString("temp_min")) - 273.15);
                int humid = Integer.parseInt(mainJSON.getString("humidity"));
                int weatherCode = Integer.parseInt(weatherJSON.getString("id"));

                TextView cityTemp = (TextView) view.findViewById(R.id.cityTemp);
                TextView cityTempHigh = (TextView) view.findViewById(R.id.cityTempHigh);
                TextView cityTempLow = (TextView) view.findViewById(R.id.cityTempLow);
                TextView cityHumidity = (TextView) view.findViewById(R.id.cityHumidity);
                ImageView cityWeatherIcon = (ImageView) view.findViewById(R.id.cityWeatherIcon);

                cityTemp.setText(Integer.toString(temp));
                cityTempHigh.setText(Integer.toString(tempHigh));
                cityTempLow.setText(Integer.toString(tempLow));
                cityHumidity.setText(Integer.toString(humid));

                /*  The "id" parameter in the JSON object outlines certain weather conditions, and
                 *  ultimately determines the icon to be displayed.
                 *
                 *  200s - thunderstorm
                 *  300s - drizzle
                 *  500s - rain
                 *  600s - snow
                 *  700s - fog, mist, misc
                 *  800  - clear
                 *  800s (except 800) - cloud
                 */

                if (weatherCode == 800) {
                    cityWeatherIcon.setImageResource(R.drawable.clear);
                } else {
                    switch (weatherCode / 100) {
                        case 2:
                            cityWeatherIcon.setImageResource(R.drawable.thunderstorm);
                            break;
                        case 3:
                            cityWeatherIcon.setImageResource(R.drawable.drizzle);
                            break;
                        case 5:
                            cityWeatherIcon.setImageResource(R.drawable.rain);
                            break;
                        case 6:
                            cityWeatherIcon.setImageResource(R.drawable.snow);
                            break;
                        case 7:
                            cityWeatherIcon.setImageResource(R.drawable.mist);
                            break;
                        case 8:
                            cityWeatherIcon.setImageResource(R.drawable.cloud);
                            break;
                        default:
                            break;
                    }
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "JSON parse error", e);
            }
        }
    }
}
