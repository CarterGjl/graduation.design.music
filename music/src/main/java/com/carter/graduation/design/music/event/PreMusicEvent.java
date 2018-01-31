package com.carter.graduation.design.music.event;

/**
 * Created by newthinkpad on 2018/1/30.
 */

public class PreMusicEvent {
    private static PreMusicEvent mPreMusicEvent = null;
    private int pre;

    private PreMusicEvent() {
    }

    public static PreMusicEvent getInstance() {
        synchronized (PreMusicEvent.class) {
            if (mPreMusicEvent == null) {
                mPreMusicEvent = new PreMusicEvent();
            }
        }
        return mPreMusicEvent;
    }

    public int getPre() {
        return pre;
    }

    public void setPre(int pre) {
        this.pre = pre;
    }
}
