package com.carter.graduation.design.music.event;

/**
 * Created by newthinkpad on 2018/1/26.
 * 当前音乐播放的进度
 */

public class MusicPositionEvent {
    private static MusicPositionEvent mMusicPositionEvent = null;
    private int currentPosition;

    private MusicPositionEvent() {
    }

    public static MusicPositionEvent getInstance() {
        synchronized (MusicPositionEvent.class) {
            if (mMusicPositionEvent == null) {
                mMusicPositionEvent = new MusicPositionEvent();
            }
        }
        return mMusicPositionEvent;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
