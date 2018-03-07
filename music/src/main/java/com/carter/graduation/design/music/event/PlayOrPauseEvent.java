package com.carter.graduation.design.music.event;

/**
 * Created by carterJL on 2018/3/6.
 */

public class PlayOrPauseEvent {
    private boolean isAppRunning;
    private boolean isPlaying;

    public boolean isAppRunning() {
        return isAppRunning;
    }

    public void setAppRunning(boolean appRunning) {
        isAppRunning = appRunning;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    private static PlayOrPauseEvent mPlayOrPauseEvent = null;
    private PlayOrPauseEvent(){}
    public static PlayOrPauseEvent getInstance(){
        synchronized(PlayOrPauseEvent.class){
            if(mPlayOrPauseEvent == null){
                mPlayOrPauseEvent = new PlayOrPauseEvent();
            }
        }
        return mPlayOrPauseEvent;
    }
}
