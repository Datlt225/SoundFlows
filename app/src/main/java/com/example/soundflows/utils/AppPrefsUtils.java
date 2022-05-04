package com.example.soundflows.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.soundflows.local.AppPrefs;

public class AppPrefsUtils {
    public static SharedPreferences sp;

    public static void createAppPrefs(Context context){

        sp = AppPrefs.getInstance(context);
    }

    public static void putString(String key, String data) {
        sp.edit().putString(key, data).commit();
    }

    public static String getString(String key) {
        return sp.getString(key, null);
    }
}
