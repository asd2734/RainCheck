package com.alex.raincheck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alex.raincheck.tasks.GetCitiesTask;
import com.alex.raincheck.utils.LocalStorage;
import com.alex.raincheck.adapters.SearchListAdapter;

public class NewCityActivity extends AppCompatActivity {
    static final String LOG_TAG = NewCityActivity.class.getName();

    private EditText citySearchBar;
    private ListView citySearchList;
    private GetCitiesTask searchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_city);

        citySearchList = (ListView) findViewById(R.id.citySearchList);
        citySearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int cityID = ((SearchListAdapter) parent.getAdapter()).getCityTuple(position).cityID;
                LocalStorage.addCity(cityID);
                Intent intent = new Intent();
                intent.setClass(NewCityActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        citySearchBar = (EditText) findViewById(R.id.citySearchBar);
        citySearchBar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchTask = new GetCitiesTask(NewCityActivity.this, citySearchList);
                    searchTask.execute(citySearchBar.getText().toString().replaceAll("\\s", "\\s"));
                    handled = true;
                }

                return handled;
            }
        });
    }
}
