package com.alex.raincheck.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.raincheck.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CityListAdapter extends BaseAdapter {
    static final String LOG_TAG = CityListAdapter.class.getName();

    private Context context;
    private ArrayList< Integer > cityIDs;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView cityName;
        ImageView cityWeatherIcon;
        TextView cityTemp;
        TextView cityTempHigh;
        TextView cityTempLow;
        TextView cityHumidity;
        WeakReference< GetCurrentWeatherTask > taskReference;
    }

    public CityListAdapter(Context context, ArrayList< Integer > cityIDs) {
        this.context = context;
        this.cityIDs = cityIDs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_city_list, null);

            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.cityName);
            holder.cityWeatherIcon = (ImageView) convertView.findViewById(R.id.cityWeatherIcon);
            holder.cityTemp = (TextView) convertView.findViewById(R.id.cityTemp);
            holder.cityTempHigh = (TextView) convertView.findViewById(R.id.cityTempHigh);
            holder.cityTempLow = (TextView) convertView.findViewById(R.id.cityTempLow);
            holder.cityHumidity = (TextView) convertView.findViewById(R.id.cityHumidity);

            GetCurrentWeatherTask cityWeatherTask = new GetCurrentWeatherTask(convertView);
            cityWeatherTask.execute(cityIDs.get(position));


            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return cityIDs.size();
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
