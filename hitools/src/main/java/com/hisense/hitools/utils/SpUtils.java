package com.hisense.hitools.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liudunjian on 2018/6/13.
 */

public class SpUtils {

    public static final String SP_ACCOUNT_KEY = "SP_ACCOUNT_KEY";
    private static final String SP_APP_KEY = "SP_APP_KEY";
    private static final String FILE_NAME = "share_data";


    private static Map<String, SpUtils> spUtilsMap = new HashMap<>();

    private SharedPreferences sp;

    private SpUtils() {
        sp = HiUtils.getContext().getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
    }

    public static SpUtils getInstance(String key) {

        if (EmptyUtils.isEmpty(key)) {
            key = SP_APP_KEY;
        }

        if (spUtilsMap.containsKey(key)) {
            return spUtilsMap.get(key);
        } else {
            SpUtils spUtils = new SpUtils();
            spUtilsMap.put(key, spUtils);
            return spUtils;
        }
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putInteger(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key,defaultValue);
    }

    public int getInteger(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public void cleanAll() {
        for (SpUtils spUtils : spUtilsMap.values())
            spUtils.clear();
        spUtilsMap.clear();
    }

}
