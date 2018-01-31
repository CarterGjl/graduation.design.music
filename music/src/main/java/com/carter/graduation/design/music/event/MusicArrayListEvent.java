package com.carter.graduation.design.music.event;

import com.carter.graduation.design.music.info.MusicInfo;

import java.util.ArrayList;

/**
 * Created by newthinkpad on 2018/1/31.
 */

public class MusicArrayListEvent {
    private static MusicArrayListEvent mMusicArrayListEvent = null;
    private ArrayList<MusicInfo> mMusicInfos;

    private MusicArrayListEvent() {
    }

    public static MusicArrayListEvent getInstance() {
        synchronized (MusicArrayListEvent.class) {
            if (mMusicArrayListEvent == null) {
                mMusicArrayListEvent = new MusicArrayListEvent();
            }
        }
        return mMusicArrayListEvent;
    }

    public ArrayList<MusicInfo> getMusicInfos() {
        return mMusicInfos;
    }

    public void setMusicInfos(ArrayList<MusicInfo> musicInfos) {
        mMusicInfos = musicInfos;
    }
}
