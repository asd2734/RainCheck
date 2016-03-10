package com.alex.raincheck.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
                sc.close();
            } else {
                file.createNewFile();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "IO exception", e);
        }

        return cityList;
    }

    public static void addCity(int cityID) {
        // see if this will be a duplicate or not
        ArrayList< Integer > cityList = listCities();
        if (cityList.contains(cityID)) {
            Log.e(LOG_TAG, "duplicate add attempted");
            return;
        }

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(ROOTPATH + "/cities.txt", true));
            output.append(cityID + "\n");
            output.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "IO exception (addCity)", e);
        }
    }

    public static void removeCity(int cityID) {
        // list cities, remove one, then save the rest in a new file
        ArrayList< Integer > cityList = listCities();
        cityList.remove(cityID);

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(ROOTPATH + "/cities.txt"));
            for (int i = 0; i < cityList.size(); i++) {
                output.append(cityList.get(i) + "\n");
            }
            output.close();
        } catch (IOException e) {
            Log.e(LOG_TAG, "IO exception (removeCity)", e);
        }
    }
}
