package com.alex.raincheck.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.raincheck.R;

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
        TextView cityHumidity;
        TextView cityPrecip;
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
