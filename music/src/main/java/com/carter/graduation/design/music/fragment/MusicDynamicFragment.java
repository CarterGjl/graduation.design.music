package com.carter.graduation.design.music.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carter.graduation.design.music.event.MusicArrayListEvent;
import com.carter.graduation.design.music.info.MusicInfo;
import com.carter.graduation.design.music.utils.UiUtil;
import com.carter.graduation.design.music.widget.ShakeListener;
import com.carter.graduation.design.music.widget.StellarMap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Random;

;


public class MusicDynamicFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private StellarMap mStellarMap;

    public MusicDynamicFragment() {
        // Required empty public constructor
    }

    public static MusicDynamicFragment newInstance(String param1, String param2) {
        MusicDynamicFragment fragment = new MusicDynamicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mContext = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        innitStellar();
        return mStellarMap;
    }

    /**
     * 初始化随机布局及
     */
    private void innitStellar() {
        mStellarMap = new StellarMap(UiUtil.getContext());
        mStellarMap.setRegularity(6, 9);
        int padding = UiUtil.dip2px(10);
        mStellarMap.setInnerPadding(padding, padding, padding, padding);
        //设置页面

        ShakeListener shakeListener = new ShakeListener(UiUtil.getContext());
        shakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                mStellarMap.zoomIn();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMusicArrayListEvent(MusicArrayListEvent musicArrayListEvent) {
        if (musicArrayListEvent != null) {
            ArrayList<MusicInfo> musicInfos = musicArrayListEvent.getMusicInfos();
            mStellarMap.setAdapter(new RecommendAdapter(musicInfos));
            mStellarMap.setGroup(0, true);
        }
    }

   /* public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

    class RecommendAdapter implements StellarMap.Adapter {
        private ArrayList<MusicInfo> mMusicInfos;

        RecommendAdapter(ArrayList<MusicInfo> mMusicInfos) {
            this.mMusicInfos = mMusicInfos;
        }

        @Override
        public int getGroupCount() {
            return 3;
        }

        @Override
        public int getCount(int group) {
            int count = mMusicInfos.size() / getGroupCount();
            if (group == getGroupCount() - 1) {
                count += mMusicInfos.size() % getGroupCount();
            }

            return count;
        }

        @Override
        public View getView(int group, int position, View convertView) {
            position += (group) * getCount(group - 1);
            final MusicInfo musicInfo = mMusicInfos.get(position);
            TextView view = new TextView(UiUtil.getContext());
            view.setText(musicInfo.getTitle());
            //随机大小
            Random random = new Random();
            int size = random.nextInt(10) + 16;
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            //rgb 颜色值不能太小或者太大 过亮过暗
            int r = random.nextInt(200) + 30;
            int g = random.nextInt(200) + 30;
            int b = random.nextInt(200) + 30;
            view.setTextColor(Color.rgb(r, g, b));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(UiUtil.getContext(), musicInfo.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }

        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if (isZoomIn) {
                //下滑上一页
                if (group > 0) {
                    group--;
                } else {
                    group = getGroupCount() - 1;
                }
            } else {
                //下滑
                if (group < getGroupCount() - 1) {
                    group++;
                } else {
                    group = 0;
                }
            }
            return group;
        }
    }
}
