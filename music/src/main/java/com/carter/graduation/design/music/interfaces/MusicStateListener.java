package com.carter.graduation.design.music.interfaces;


public interface MusicStateListener {

    /**
     * 更新歌曲状态信息
     */
    void updateTrackInfo();

    void updateTime();

    void changeTheme();

    void reloadAdapter();
}
