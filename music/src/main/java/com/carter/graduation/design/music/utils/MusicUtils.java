package com.carter.graduation.design.music.utils;

import android.database.Cursor;
import android.provider.MediaStore;

import com.carter.graduation.design.music.info.MusicInfo;

import java.util.ArrayList;

/**
 * Created by newthinkpad on 2018/1/19.
 * 用于检索音乐文件
 */

public class MusicUtils {
    private static int count = 0;

    public static ArrayList<MusicInfo> scanAllMusicFiles() {
        ArrayList<MusicInfo> musicInfos = new ArrayList<>();
        Cursor cursor = UiUtil.getContext().getContentResolver().query
                (MediaStore.Audio
                                .Media.EXTERNAL_CONTENT_URI, null, null, null,
                        MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        ._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .ALBUM));
               /* int albumId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .ALBUM_ID));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .ARTIST));*/
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .DATA));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .DURATION));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .SIZE));
                if (size > 1024 * 80) {
                    //大于80k
                    MusicInfo musicInfo = new MusicInfo();
                    musicInfo.setId(id);
                    musicInfo.setAlbum(album);
                    musicInfo.setDuration(duration);
                    musicInfo.setSize(size);
                    musicInfo.setTitle(title);
                    musicInfo.setUrl(url);
                    musicInfos.add(musicInfo);
                    count++;
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return musicInfos;
    }
}
