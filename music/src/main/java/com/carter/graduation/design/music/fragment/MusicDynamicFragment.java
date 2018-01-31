package com.carter.graduation.design.music.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.carter.graduation.design.music.R;
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


public class MusicDynamicFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int SCAN_MUSIC_LIST = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    //    private ArrayList<MusicInfo> mMusicInfos;
   /* @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_MUSIC_LIST:
                    mStellarMap.setAdapter(new RecommendAdapter(mMusicInfos));
                    mStellarMap.setGroup(0,true);
                   *//* MusicInfo musicInfo = mMusicInfos.get(0);
                    playMusic(musicInfo,MusicState.State.PLAYING);
                    mTvMusicTitle.setText(musicInfo.getTitle());*//*
                    break;
//                    最初的进度进度条逻辑
                *//*case IS_PLAYING:
                    mProgress = mPbProgress.getProgress();
                    startUpdateSeekBarProgress(mProgress);
                    mPbProgress.setProgress(mPbProgress.getProgress() + 100);
                    break;*//*
                default:
                    break;
            }
        }
    };*/
    private Context mContext;
    private StellarMap mStellarMap;

    public MusicDynamicFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MusicDynamicFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MusicDynamicFragment newInstance(String param1, String param2) {
        MusicDynamicFragment fragment = new MusicDynamicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /*   private void loadLocalMusic() {
           new Thread(new Runnable() {
               @Override
               public void run() {
                   mMusicInfos = MusicUtils.scanAllMusicFiles();
                   Message obtain = Message.obtain();
                   obtain.what = SCAN_MUSIC_LIST;
                   mHandler.sendEmptyMessage(SCAN_MUSIC_LIST);
               }
           }).start();
       }*/
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
        // Inflate the layout for this fragment
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

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

    private void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.create()
                .setIcon(R.drawable.icon);
        builder.setCancelable(false)
                .setMessage("本app需要此权限否则无法使用")
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest
                                .permission.READ_EXTERNAL_STORAGE}, 1);
                    }
                });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /*Toast.makeText(mContext, "本app需要此权限否则无法使用,请前往系统设置开启",
                        Toast.LENGTH_SHORT).show();*/
                Snackbar.make(mStellarMap, "本app需要此权限否则无法使用,请前往系统设置开启", Snackbar.LENGTH_LONG)
                        .setAction("允许使用权限", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                goToSetting();
                            }
                        }).show();


            }
        });
        builder.show();
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadLocalMusic();

                } else {
                    showPermissionDialog();
                    //Toast.makeText(this, "本app需要此权限否则无法使用", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }*/

    private void goToSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        startActivity(localIntent);
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


   /* private void iniUse() {
        if (ContextCompat.checkSelfPermission(UiUtil.getContext(), Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest
                    .permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            //扫描本地歌曲库并显示  注意需要权限
            if (((boolean) SpUtils.get(mContext, GlobalConstants.IS_FIRST_USE, true))) {
                loadLocalMusic();
                SpUtils.put(UiUtil.getContext(), GlobalConstants.IS_FIRST_USE, false);
            } else {
                loadLocalMusic();
            }
        }
    }*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

    class RecommendAdapter implements StellarMap.Adapter {
        private ArrayList<MusicInfo> mMusicInfos;

        RecommendAdapter(ArrayList<MusicInfo> mMusicInfos) {
            this.mMusicInfos = mMusicInfos;
        }

        @Override
        public int getGroupCount() {
            return 2;
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
