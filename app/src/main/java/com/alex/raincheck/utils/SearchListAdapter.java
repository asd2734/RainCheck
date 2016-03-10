package com.alex.raincheck.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.raincheck.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter {
    static final String LOG_TAG = SearchListAdapter.class.getName();

    private Context context;
    private ArrayList<CityTuple> cityTuples;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView cityName;
        TextView cityCountry;
    }

    public SearchListAdapter(Context context, ArrayList<CityTuple> cityTuples) {
        this.context = context;
        this.cityTuples = cityTuples;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CityTuple getCityTuple(int position) {
        return cityTuples.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_search_list, null);

            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.searchCityName);
            holder.cityName.setText(cityTuples.get(position).cityName);
            holder.cityCountry = (TextView) convertView.findViewById(R.id.searchCityCountry);
            holder.cityCountry.setText(cityTuples.get(position).cityCountry);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getCount() { return cityTuples.size(); }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
