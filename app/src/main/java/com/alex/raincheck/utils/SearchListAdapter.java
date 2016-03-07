package com.alex.raincheck.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alex.raincheck.R;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by alex on 3/6/16.
 */
public class SearchListAdapter extends BaseAdapter {
    static final String LOG_TAG = SearchListAdapter.class.getName();

    private Context context;
    private ArrayList< CityNameIDPair > cityPairs;
    private LayoutInflater inflater;

    private class ViewHolder {
        TextView cityName;
        WeakReference< GetCitiesTask > taskReference;
    }

    public SearchListAdapter(Context context, ArrayList< CityNameIDPair > cityPairs) {
        this.context = context;
        this.cityPairs = cityPairs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_search_list, null);

            holder = new ViewHolder();
            holder.cityName = (TextView) convertView.findViewById(R.id.searchCityName);
            holder.cityName.setText(cityPairs.get(position).cityName);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        return 0;
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
