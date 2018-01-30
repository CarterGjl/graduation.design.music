package com.carter.graduation.design.music;

import com.carter.graduation.design.music.utils.ActivityCollectorUtils;

import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by newthinkpad on 2018/1/30.
 * 用于关闭音乐
 */

public class TimerExit {
    private static TimerExit mTimerExit = null;
    private Timer mTimer;
    private ScheduledExecutorService mExecutor;
    private Runnable mExitTask;

    private TimerExit() {
    }

    public static TimerExit getInstance() {
        synchronized (TimerExit.class) {
            if (mTimerExit == null) {
                mTimerExit = new TimerExit();
            }
        }
        return mTimerExit;
    }

    public void startTask(int delay) {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mExitTask == null) {
            mExitTask = new Runnable() {
                @Override
                public void run() {
                    ActivityCollectorUtils.finishAll();
                }
            };
        }
        mExecutor.schedule(mExitTask, delay, TimeUnit.MINUTES);
    }

    public void cancelTask() {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mExitTask = null;
        }
    }
}
