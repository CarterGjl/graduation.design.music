package com.carter.graduation.design.music.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.carter.graduation.design.music.info.MusicInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by carterJL on 2017/3/4.
 */
public class SearchUtils {
    public static ArrayList<MusicInfo> searchSongs(Context context, String searchString) {
        return getSongsForCursor(makeSongCursor(context, "title LIKE ?", new String[]{"%" + searchString + "%"}));
    }

    public static ArrayList<MusicInfo> getSongsForCursor(Cursor cursor) {
        ArrayList arrayList = new ArrayList();
        if ((cursor != null) && (cursor.moveToFirst()))
            do {
                MusicInfo musicInfo = new MusicInfo();
                musicInfo.setId((int) cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media
                        ._ID)));
                musicInfo.setAlbum_id(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
                musicInfo.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
//                musicInfo.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                musicInfo.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                arrayList.add(musicInfo);
            }
            while (cursor.moveToNext());
        if (cursor != null)
            cursor.close();
        return arrayList;
    }

    public static Cursor makeSongCursor(Context context, String selection, String[] paramArrayOfString) {
        String selectionStatement = "is_music=1 AND title != ''";
        // final String songSortOrder = PreferencesUtility.getInstance(context).getSongSortOrder();

        if (!TextUtils.isEmpty(selection)) {
            selectionStatement = selectionStatement + " AND " + selection;
        }
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "title", "artist", "album", "duration", "track", "artist_id", "album_id"}, selectionStatement, paramArrayOfString, null);

        return cursor;
    }
    public static ArrayList<MusicInfo> queryMusic(Context context, String key){
        ArrayList<MusicInfo> musicList = new ArrayList<>();
        Cursor cursor = null;
        cursor= context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,
                MediaStore.Audio.Media.DISPLAY_NAME + " LIKE '%" + key + "%'",null,MediaStore.Audio
                        .Media.DEFAULT_SORT_ORDER);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        ._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .ALBUM));
                int albumId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .ALBUM_ID));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media
                        .ARTIST));
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
        return musicList;
    }
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}
