package com.carter.graduation.design.music.event;

/**
 * Created by newthinkpad on 2018/1/22.
 * 需要传递的数据
 */

public class MusicEvent {

    private static MusicEvent mMusicEvent = null;
    private String path;
    private int musicState;
    private int resetProgress;

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

    public int getResetProgress() {
        return resetProgress;
    }

    public void setResetProgress(int resetProgress) {
        this.resetProgress = resetProgress;
    }

    public int getMusicState() {
        return musicState;
    }

    public void setMusicState(int musicState) {
        this.musicState = musicState;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
