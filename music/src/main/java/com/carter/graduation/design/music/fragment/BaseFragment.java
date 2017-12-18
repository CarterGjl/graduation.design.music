package com.carter.graduation.design.music.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.carter.graduation.design.music.interfaces.MusicStateListener;


public class BaseFragment extends Fragment implements MusicStateListener {
    public Activity mContext;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void updateTrackInfo() {

    }

    @Override
    public void updateTime() {

    }

    @Override
    public void changeTheme() {

    }

    @Override
    public void reloadAdapter() {

    }
}
