package com.carter.graduation.design.music.utils;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Process;
import android.view.View;

import com.carter.graduation.design.music.global.MusicApplication;

public class UiUtil {

    public static Context getContext() {
        return MusicApplication.getContext();
    }

    public static Handler getHandler() {
        return MusicApplication.getHandler();
    }

    public static int getMainThreadId() {
        return MusicApplication.getMainThreadId();
    }

    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id, null);
    }

    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (density * dip + 0.5f);
    }

    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return px / density;
    }

    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    public static boolean isRunUIThread() {
        //拿到当前线程id  与主线程id比较
        int myTid = Process.myTid();
        if (myTid == getMainThreadId()) {
            return true;
        }
        return false;
    }

    //自动到主线程
    public static void runOnUIThread(Runnable runnable) {
        if (isRunUIThread()) {
            runnable.run();
        } else {
            //post后就运行在主线程  通过handler让其运行在主线程
            getHandler().post(runnable);
        }
    }

    public static ColorStateList getColorStateList(int id) {

        return getContext().getResources().getColorStateList(id);
    }
}
