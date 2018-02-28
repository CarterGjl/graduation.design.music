package com.carter.graduation.design.music.event;

/**
 * Created by carter on 2018/2/28.
 * 音乐播放的事件
 */

public class MusicStartEvent {

    //当前播放的music

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private static MusicStartEvent mMusicStartEvent = null;
    private MusicStartEvent(){}
    public static MusicStartEvent getInstance(){
        synchronized(MusicStartEvent.class){
            if(mMusicStartEvent == null){
                mMusicStartEvent = new MusicStartEvent();
            }
        }
        return mMusicStartEvent;
    }
}
