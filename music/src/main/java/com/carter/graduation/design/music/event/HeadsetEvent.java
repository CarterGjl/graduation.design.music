package com.carter.graduation.design.music.event;

/**
 * Created by newthinkpad on 2018/1/23.
 * 耳机插拔事件
 */

public class HeadsetEvent {
    private static HeadsetEvent mHeadsetEvent = null;
    private int headsetState;

    private HeadsetEvent() {
    }

    public static HeadsetEvent getInstance() {
        synchronized (HeadsetEvent.class) {
            if (mHeadsetEvent == null) {
                mHeadsetEvent = new HeadsetEvent();
            }
        }
        return mHeadsetEvent;
    }

    public int getHeadsetState() {
        return headsetState;
    }

    public void setHeadsetState(int headsetState) {
        this.headsetState = headsetState;
    }
}
