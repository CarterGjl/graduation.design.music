package com.carter.graduation.design.music.event;

import com.carter.graduation.design.music.info.MusicInfo;

/**
 * Created by carter on 2018/3/12.
 */

public class SearchMusicInfoEvent {
    private MusicInfo mMusicInfo;
    private static SearchMusicInfoEvent mSearchMusicInfoEvent = null;
    private SearchMusicInfoEvent(){}
    public static SearchMusicInfoEvent getInstance(){
        synchronized(SearchMusicInfoEvent.class){
            if(mSearchMusicInfoEvent == null){
                mSearchMusicInfoEvent = new SearchMusicInfoEvent();
            }
        }
        return mSearchMusicInfoEvent;
    }

    public MusicInfo getMusicInfo() {
        return mMusicInfo;
    }

    public void setMusicInfo(MusicInfo musicInfo) {
        mMusicInfo = musicInfo;
    }
}
