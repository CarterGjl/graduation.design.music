package com.carter.graduation.design.music.event;


import com.carter.graduation.design.music.player.MusicState;

public class MusicStateEvent {
    private static MusicStateEvent mMusicStateEvent = null;
    private @MusicState.State
    int mState;

    private MusicStateEvent() {
    }

    public static MusicStateEvent getInstance() {
        synchronized (MusicStateEvent.class) {
            if (mMusicStateEvent == null) {
                mMusicStateEvent = new MusicStateEvent();
            }
        }
        return mMusicStateEvent;
    }

    public @MusicState.State
    int getState() {
        return mState;
    }

    public void setState(@MusicState.State int state) {
        mState = state;
    }
}
