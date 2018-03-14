package com.carter.graduation.design.music.global;

import com.carter.graduation.design.music.info.MusicInfo;

import java.util.ArrayList;

/**
 * Created by newthinkpad on 2018/1/22.
 * app 用到的全局变量
 */

public class GlobalConstants {
    public static final String IS_FIRST_USE = "is_first_use";
    private static final String CURRENT_MUSIC = "current_music";
    public static String IS_RANDOM = "is_random";
    public static ArrayList<MusicInfo> sMusicInfos = new ArrayList<>();
}
