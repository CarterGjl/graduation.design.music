package com.carter.graduation.design.music.utils;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by newthinkpad on 2018/1/30.
 */

public class ActivityCollectorUtils {
    public static ArrayList<Activity> sActivities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        sActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        sActivities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : sActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
