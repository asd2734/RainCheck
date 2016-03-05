package com.alex.raincheck.utils;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class LocalStorage {
    static final String LOG_TAG = LocalStorage.class.getName();

    private static final String ROOTPATH = Environment.getExternalStorageDirectory() + "/RainCheck";

    public static String getRootPath() { return ROOTPATH; }

    public static ArrayList<String> listCities() {
        ArrayList< String > cityList = new ArrayList< String >();
        File dir = new File(ROOTPATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File[] files = dir.listFiles();
        for(File file : files){
            if(file.isDirectory()){
                cityList.add(file.getName());
            }
        }

        Collections.sort(cityList);
        return cityList;
    }
}
