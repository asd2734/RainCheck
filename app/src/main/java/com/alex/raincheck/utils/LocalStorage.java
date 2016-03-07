package com.alex.raincheck.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class LocalStorage {
    static final String LOG_TAG = LocalStorage.class.getName();

    private static final String ROOTPATH = Environment.getExternalStorageDirectory() + "/RainCheck";

    public static String getRootPath() { return ROOTPATH; }

    public static ArrayList< Integer > listCities() {
        ArrayList< Integer > cityList = new ArrayList< Integer >();
        File dir = new File(ROOTPATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(ROOTPATH + "/cities.txt");
        try {
            if (file.exists()) {
                Scanner sc = new Scanner(file);
                while (sc.hasNextLine()) {
                    cityList.add(Integer.parseInt(sc.nextLine()));
                }
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IO exception", e);
        }

        Collections.sort(cityList);
        return cityList;
    }
}
