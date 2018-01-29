package com.carter.graduation.design.music.info;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by newthinkpad on 2018/1/18.
 * 音乐信息
 */

public class MusicInfo implements Parcelable {


    //数据库中的id

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel in) {
            MusicInfo musicInfo = new MusicInfo();
            musicInfo.title = in.readString();
            musicInfo.album = in.readString();
            musicInfo.url = in.readString();
            musicInfo.id = in.readInt();
            musicInfo.duration = in.readInt();
            musicInfo.size = in.readLong();
            musicInfo.album_id = in.readInt();
            return musicInfo;
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };
    private int id;
    private String title;
    //专辑名字
    private String album;
    private int album_id;
    private String url;
    private int duration;
    private long size;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(album);
        parcel.writeString(url);
        parcel.writeInt(id);
        parcel.writeInt(duration);
        parcel.writeLong(size);
        parcel.writeInt(album_id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }
}
