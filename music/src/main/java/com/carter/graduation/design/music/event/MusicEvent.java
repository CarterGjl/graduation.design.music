package com.carter.graduation.design.music.event;

/**
 * Created by newthinkpad on 2018/1/22.
 * 需要传递的数据
 */

public class MusicEvent {

    private static MusicEvent mMusicEvent = null;
    private String path;

    private MusicEvent() {
    }

    public static MusicEvent getInstance() {
        synchronized (MusicEvent.class) {
            if (mMusicEvent == null) {
                mMusicEvent = new MusicEvent();
            }
        }
        return mMusicEvent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
