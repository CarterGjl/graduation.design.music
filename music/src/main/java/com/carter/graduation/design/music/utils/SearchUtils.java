package com.carter.graduation.design.music.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;


import com.carter.graduation.design.music.info.MusicInfo;

import java.util.ArrayList;

/**
 * Created by carterJL on 2017/3/4
 */
public class SearchUtils {
    public static ArrayList<MusicInfo> queryMusic(Context context, String key){
        ArrayList<MusicInfo> musicList = new ArrayList<>();
        Cursor cursor;
        cursor= context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Audio.Media.DISPLAY_NAME + " LIKE '%" + key + "%'",null,MediaStore.Audio
                        .Media.DEFAULT_SORT_ORDER);
        assert cursor != null;
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        ._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .ALBUM));
                int albumId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .ALBUM_ID));
                /*String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
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
                    musicInfo.setAlbum_id(albumId);
                    /*if (!SearchUtils.isExists(url)){
                        musicList.add(musicInfo);
                    }*/
                    musicList.add(musicInfo);
                }
                cursor.moveToNext();
            }
        }
        cursor.close();
        return musicList;
    }
}
