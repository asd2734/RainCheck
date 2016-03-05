package com.alex.raincheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewCityActivity extends AppCompatActivity {
    static final String LOG_TAG = NewCityActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_city);
    }
}
