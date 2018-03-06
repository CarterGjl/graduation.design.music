package com.carter.graduation.design.music.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.info.MusicInfo;
import com.carter.graduation.design.music.utils.UiUtil;

import java.util.ArrayList;

/**
 * Created by carter on 2018/3/6.
 */

public class SearchAdapter extends Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<MusicInfo> mMusicInfos = new ArrayList<>();
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(UiUtil.getContext()).inflate(R.layout.list_music_info, parent,
                false);
        return new ViewHolder(view);
    }

    public void updateSearchResults(ArrayList<MusicInfo> searchResults) {
        this.mMusicInfos = searchResults;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        final MusicInfo musicInfo = mMusicInfos.get(position);
        holder.tvTitle.setText(musicInfo.getTitle());
        holder.tvArtist.setText(musicInfo.getAlbum());
    }

    @Override
    public int getItemCount() {
        return mMusicInfos.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
         CardView cardView;
         TextView tvTitle;
         TextView tvArtist;
         ImageView ivMusic;
         ImageView ivPlayState;
         public ViewHolder(View itemView) {
             super(itemView);
             cardView = (CardView) itemView;
             tvTitle = itemView.findViewById(R.id.tv_title);
             tvArtist = itemView.findViewById(R.id.tv_artist);
             ivMusic = itemView.findViewById(R.id.iv_music_album);
             ivPlayState = itemView.findViewById(R.id.play_state);
         }
     }
}
