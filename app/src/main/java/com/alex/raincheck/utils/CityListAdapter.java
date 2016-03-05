package com.alex.raincheck.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.raincheck.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class CityListAdapter extends BaseAdapter {
    static final String LOG_TAG = CityListAdapter.class.getName();

    private Context context;
    private ArrayList<String> cityNames;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView cityName;
        ImageView cityWeatherIcon;
        TextView cityTemp;
        TextView cityTempHigh;
        TextView cityTempLow;
        TextView cityHumidity;
    }

    public CityListAdapter(Context context, ArrayList<String> recordingTitles) {
        this.context = context;
        this.cityNames = recordingTitles;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_city_list, null);

            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.cityName);
            holder.cityName.setText(cityNames.get(position));
            holder.cityWeatherIcon = (ImageView) convertView.findViewById(R.id.cityWeatherIcon);
            holder.cityTemp = (TextView) convertView.findViewById(R.id.cityTemp);
            holder.cityTempHigh = (TextView) convertView.findViewById(R.id.cityTempHigh);
            holder.cityTempLow = (TextView) convertView.findViewById(R.id.cityTempLow);
            holder.cityHumidity = (TextView) convertView.findViewById(R.id.cityHumidity);

            HttpWeather cityWeather = new HttpWeather(cityNames.get(position));
            try {
                JSONObject cityWeatherJSON = new JSONObject(cityWeather.getCurrentWeatherJSON());
                // Temperature and humidity data are under "main"
                JSONObject mainJSON = cityWeatherJSON.getJSONObject("main");
                // Temperatures in JSON are in Kelvins
                int temp = (int) (Double.parseDouble(mainJSON.getString("temp")) - 273.15);
                int tempHigh = (int) (Double.parseDouble(mainJSON.getString("temp_max")) - 273.15);
                int tempLow = (int) (Double.parseDouble(mainJSON.getString("temp_min")) - 273.15);
                int humid = Integer.parseInt(mainJSON.getString("humidity"));

            } catch (Exception e) {
                Log.e(LOG_TAG, "JSON parse error", e);
            }


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return cityNames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
