package com.carter.graduation.design.music.event;

/**
 * Created by newthinkpad on 2018/1/29.
 * 用于传递进度的事件
 */

public class SeekBarEvent {
    private static SeekBarEvent mSeekBarEvent = null;
    private int seekBarPosition;
    private int userSelectedPosition;

    private SeekBarEvent() {
    }

    public static SeekBarEvent getInstance() {
        synchronized (SeekBarEvent.class) {
            if (mSeekBarEvent == null) {
                mSeekBarEvent = new SeekBarEvent();
            }
        }
        return mSeekBarEvent;
    }

    public int getUserSelectedPosition() {
        return userSelectedPosition;
    }

    public void setUserSelectedPosition(int userSelectedPosition) {
        this.userSelectedPosition = userSelectedPosition;
    }

    public int getSeekBarPosition() {
        return seekBarPosition;
    }

    public void setSeekBarPosition(int seekBarPosition) {
        this.seekBarPosition = seekBarPosition;
    }
}
