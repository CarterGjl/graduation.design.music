package com.carter.graduation.design.music.player;

/**
 * 音乐播放接口
 * Created by newthinkpad on 2018/1/25.
 */

public interface PlayAdapter {

    void loadMedia(String path);

    void release();

    void reset(String path);

    boolean isPlaying();

    void play();

    void initPreparePlay();

    void continuePlaying();

    void pause();

    void initializeProgressCallback();

    void seekTo(int position);
}
