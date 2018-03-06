package com.carter.graduation.design.music.fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.event.MusicArrayListEvent;
import com.carter.graduation.design.music.event.MusicEvent;
import com.carter.graduation.design.music.info.MusicInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by carter on 2018/3/5.
 */

public class SearchMusicFragment extends DialogFragment {
    private Context mContext;
    private RecyclerView mRvMusic;
    private static final String TAG = "SearchMusicFragment";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext =context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_search_music, container, false);
        mRvMusic = (RecyclerView) view.findViewById(R.id.rv_search_music);
        mRvMusic.setLayoutManager(new LinearLayoutManager(mContext));
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.8);
        int dialogWidth = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.8);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(dialogWidth, dialogHeight);
        }
//        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);
    }
    class MusicRvAdapter extends RecyclerView.Adapter<MusicRvAdapter.ViewHolder>{
        ArrayList<MusicInfo> mMusicInfos;

        public MusicRvAdapter(ArrayList<MusicInfo> musicInfos) {
            mMusicInfos = musicInfos;
        }

        @Override
        public MusicRvAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_music_info, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MusicRvAdapter.ViewHolder holder, int position) {
            final MusicInfo musicInfo = mMusicInfos.get(position);
            holder.tvTitle.setText(musicInfo.getTitle());
            holder.tvArtist.setText(musicInfo.getAlbum());
        }

        @Override
        public int getItemCount() {
            return mMusicInfos.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

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
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void onGetMusicInfo(MusicArrayListEvent event){
        ArrayList<MusicInfo> musicInfos = event.getMusicInfos();
        MusicRvAdapter musicRvAdapter = new MusicRvAdapter(musicInfos);
        Log.d(TAG, "onGetMusicInfo: "+musicInfos.get(0).getTitle());
        mRvMusic.setAdapter(musicRvAdapter);
    }
}
