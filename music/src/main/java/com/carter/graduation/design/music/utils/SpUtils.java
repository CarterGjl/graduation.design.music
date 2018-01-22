package com.carter.graduation.design.music.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by newthinkpad on 2018/1/22.
 * sp
 */

public class SpUtils {
    private static final String FILE_NAME = "music_data";

    public static void put(Context context, String key, Object o) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (o instanceof String) {
            editor.putString(key, ((String) o));
        } else if (o instanceof Integer) {
            editor.putInt(key, ((Integer) o));
        } else if (o instanceof Boolean) {
            editor.putBoolean(key, ((Boolean) o));
        } else if (o instanceof Float) {
            editor.putFloat(key, ((Float) o));
        } else if (o instanceof Long) {
            editor.putLong(key, ((Long) o));
        }
        editor.apply();
    }

    public static Object get(Context context, String key, Object defaultValue) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultValue instanceof String) {
            return sp.getString(key, ((String) defaultValue));
        } else if (defaultValue instanceof Integer) {
            return sp.getInt(key, ((Integer) defaultValue));
        } else if (defaultValue instanceof Boolean) {
            return sp.getBoolean(key, ((Boolean) defaultValue));
        } else if (defaultValue instanceof Float) {
            return sp.getFloat(key, ((Float) defaultValue));
        } else if (defaultValue instanceof Long) {
            return sp.getLong(key, ((Long) defaultValue));
        }
        return null;
    }
}
