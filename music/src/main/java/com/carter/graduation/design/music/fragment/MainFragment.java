package com.carter.graduation.design.music.fragment;


import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carter.graduation.design.music.R;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends BaseFragment {

    private MainFragmentAdapter mAdapter;
    private RecyclerView songList;
    private LinearLayoutManager layoutManager;
    private List<MainFragmentItem> mList = new ArrayList<>();
    private PlaylistInfo playlistInfo; //playlist 管理类
    private SwipeRefreshLayout swipeRefresh; //下拉刷新layout

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            reloadAdapter();
        }
    }

    public void reloadAdapter() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(final Void... unused) {
                ArrayList results = new ArrayList();
                setMusicInfo();
                ArrayList<Playlist> playlists = playlistInfo.getPlaylist();
                ArrayList<Playlist> netPlaylists = playlistInfo.getNetPlaylist();
                results.addAll(mList);
                results.add(mContext.getResources().getString(R.string.created_playlists));
                results.addAll(playlists);
                if (netPlaylists != null) {
                    results.add("收藏的歌单");
                    results.addAll(netPlaylists);
                }

                if (mAdapter == null) {
                    mAdapter = new MainFragmentAdapter(mContext);
                }
                mAdapter.updateResults(results, playlists, netPlaylists);
                return null;
            }

            @Overrider
            protected void onPostExecute(Void aVoid) {
                if (mContext == null)
                    return;
                mAdapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        }.execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        swipeRefresh = view.findViewById(R.id.srl_refresh);
        songList = view.findViewById(R.id.rv_song_list);

        layoutManager = new LinearLayoutManager(mContext);
        songList.setLayoutManager(layoutManager);
        swipeRefresh.setColorSchemeColors(ThemeUtils.getColorById(mContext, R.color.theme_color_primary));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reloadAdapter();

            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void changeTheme() {
        super.changeTheme();
    }
}
