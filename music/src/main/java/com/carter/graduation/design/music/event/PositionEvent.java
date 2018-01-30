package com.carter.graduation.design.music.event;

/**
 * Created by newthinkpad on 2018/1/30.
 */

public class PositionEvent {
    private static PositionEvent mPositionEvent = null;
    private int pos;

    private PositionEvent() {
    }

    public static PositionEvent getInstance() {
        synchronized (PositionEvent.class) {
            if (mPositionEvent == null) {
                mPositionEvent = new PositionEvent();
            }
        }
        return mPositionEvent;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
