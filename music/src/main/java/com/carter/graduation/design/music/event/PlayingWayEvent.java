package com.carter.graduation.design.music.event;

/**
 * Created by carter on 2018/3/12.
 */

public class PlayingWayEvent {
    private boolean isRandom;
    private static PlayingWayEvent mPlayingWayEvent = null;
    private PlayingWayEvent(){}
    public static PlayingWayEvent getInstance(){
        synchronized(PlayingWayEvent.class){
            if(mPlayingWayEvent == null){
                mPlayingWayEvent = new PlayingWayEvent();
            }
        }
        return mPlayingWayEvent;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public void setRandom(boolean random) {
        isRandom = random;
    }
}
