package com.carter.graduation.design.music.event;

/**
 * Created by newthinkpad on 2018/1/29.
 */

public class DurationEvent {
    private static int duration;
    private static DurationEvent mDurationEvent = null;

    private DurationEvent() {
    }

    public static int getDuration() {
        return duration;
    }

    public static void setDuration(int duration) {
        DurationEvent.duration = duration;
    }

    public static DurationEvent getInstance() {
        synchronized (DurationEvent.class) {
            if (mDurationEvent == null) {
                mDurationEvent = new DurationEvent();
            }
        }
        return mDurationEvent;
    }
}
