package com.carter.graduation.design.music.event;

import com.carter.graduation.design.music.info.MusicInfo;

/**
 * Created by carter on 2018/3/7.
 */

public class RandomMusicEven {

    private MusicInfo mMusicInfo;

    public MusicInfo getMusicInfo() {
        return mMusicInfo;
    }

    public void setMusicInfo(MusicInfo musicInfo) {
        mMusicInfo = musicInfo;
    }

    private static RandomMusicEven mRandomMusicEven = null;
    private RandomMusicEven(){}
    public static RandomMusicEven getInstance(){
        synchronized(RandomMusicEven.class){
            if(mRandomMusicEven == null){
                mRandomMusicEven = new RandomMusicEven();
            }
        }
        return mRandomMusicEven;
    }
}
