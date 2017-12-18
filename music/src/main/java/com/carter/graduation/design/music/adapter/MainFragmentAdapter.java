package com.carter.graduation.design.music.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.widget.TintImageView;
import com.facebook.drawee.view.SimpleDraweeView;


public class MainFragmentAdapter extends RecyclerView.Adapter<MainFragmentAdapter.ItemHolder> {
    @Override
    public MainFragmentAdapter.ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MainFragmentAdapter.ItemHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView itemtitle, title, count, songcount, sectionItem;
        protected ImageView menu, sectionImg, sectionMenu;
        protected TintImageView image;
        SimpleDraweeView albumArt;

        public ItemHolder(View view) {
            super(view);
            this.image = (TintImageView) view.findViewById(R.id.fragment_main_item_img);
            this.itemtitle = (TextView) view.findViewById(R.id.fragment_main_item_title);
            this.count = (TextView) view.findViewById(R.id.fragment_main_item_count);

            this.title = (TextView) view.findViewById(R.id.fragment_main_playlist_item_title);
            this.songcount = (TextView) view.findViewById(R.id.fragment_main_playlist_item_count);
            this.albumArt = (SimpleDraweeView) view.findViewById(R.id.fragment_main_playlist_item_img);
            this.menu = (ImageView) view.findViewById(R.id.fragment_main_playlist_item_menu);

            this.sectionItem = (TextView) view.findViewById(R.id.expand_title);
            this.sectionImg = (ImageView) view.findViewById(R.id.expand_img);
            this.sectionMenu = (ImageView) view.findViewById(R.id.expand_menu);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
