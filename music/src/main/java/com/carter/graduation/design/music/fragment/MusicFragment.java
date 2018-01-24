package com.carter.graduation.design.music.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.activity.PlayDetailActivity;
import com.carter.graduation.design.music.event.HeadsetEvent;
import com.carter.graduation.design.music.event.MusicEvent;
import com.carter.graduation.design.music.global.GlobalConstants;
import com.carter.graduation.design.music.info.MusicInfo;
import com.carter.graduation.design.music.utils.MusicUtils;
import com.carter.graduation.design.music.utils.SpUtils;
import com.carter.graduation.design.music.utils.UiUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * 音乐播放界面
 */
public class MusicFragment extends Fragment {


    private static final int SCAN_MUSIC_LIST = 1;
    private static final int IS_PLAYING = 2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "MusicFragment";
    private int currentPos = 0;
    private ProgressDialog mProgressDialog;
    private SwipeRefreshLayout mSplMusic;
    private OnFragmentInteractionListener mListener;
    private ArrayList<MusicInfo> mMusicInfos;
    private Context mContext;
    private RecyclerView mRvSongListView;
    private ImageView mIvImage;
    private TextView mTvContent;
    private ImageView mIvNext;
    private ImageView mIvPlay;
    private ImageView mIvPre;
    private ProgressBar mPbProgress;
    private int mDuration;
    private Intent mIntent;
    private MusicInfoAdapter mMusicInfoAdapter;
    private int mProgress;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SCAN_MUSIC_LIST:
                    mRvSongListView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    //获取传递的数据

                    mMusicInfoAdapter = new MusicInfoAdapter(mMusicInfos);
                    mRvSongListView.setAdapter(mMusicInfoAdapter);
                    //musicInfoAdapter.notifyDataSetChanged();
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                    mSplMusic.setRefreshing(false);
                    break;
                case IS_PLAYING:
                    mProgress = mPbProgress.getProgress();
                    startUpdateSeekBarProgress(mProgress);
                    mPbProgress.setProgress(mPbProgress.getProgress() + 100);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);

        }
    };
    private boolean isPlaying = false;
    //当前播放音乐的路径
    private MusicInfo mMusicInfoCurrent;
    private int mPauseProgress;

    public MusicFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param musicInfos 音乐列表.
     * @param param2     Parameter 2.
     * @return A new instance of fragment MusicFragment.
     */
    public static MusicFragment newInstance(ArrayList<MusicInfo> musicInfos, String param2) {
        MusicFragment fragment = new MusicFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, musicInfos);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 用于显示扫描音乐的dialog
     */
    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage("扫描音乐ing  请等候");
        mProgressDialog.setTitle("提示");
        mProgressDialog.setIcon(R.drawable.small_icon);
        mProgressDialog.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mContext = getActivity();
        if (getArguments() != null) {
            //mMusicInfos = getArguments().getParcelableArrayList(ARG_PARAM1);
//            Log.d(TAG, "onCreateView: "+mMusicInfos.get(0).getTitle());
            // mParam2 = getArguments().getString(ARG_PARAM2);
        }
        if (ContextCompat.checkSelfPermission(UiUtil.getContext(), Manifest.permission
                .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest
                    .permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            //扫描本地歌曲库并显示  注意需要权限
            if (((boolean) SpUtils.get(mContext, GlobalConstants.IS_FIRST_USE, true))) {
                showProgressDialog();
                scanLocalMusic();
                SpUtils.put(mContext, GlobalConstants.IS_FIRST_USE, false);
//                mMusicInfoCurrent = (String) SpUtils.get(mContext,GlobalConstants.CURRENT_MUSIC,mMusicInfos.get(0)
//                        .getUrl());
            } else {
                loadLocalMusic();
//                mMusicInfoCurrent = (String) SpUtils.get(mContext,GlobalConstants.CURRENT_MUSIC,mMusicInfos.get(0)
//                        .getUrl());
            }

        }
    }

    private void scanLocalMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //这里为了让大家能看到进度条  让进程睡眠2s
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mMusicInfos = MusicUtils.scanAllMusicFiles();
                Message obtain = Message.obtain();
                obtain.what = SCAN_MUSIC_LIST;
                mHandler.sendEmptyMessage(SCAN_MUSIC_LIST);
            }
        }).start();
    }

    private void loadLocalMusic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMusicInfos = MusicUtils.scanAllMusicFiles();
                Message obtain = Message.obtain();
                obtain.what = SCAN_MUSIC_LIST;
                mHandler.sendEmptyMessage(SCAN_MUSIC_LIST);
            }
        }).start();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        mRvSongListView = view.findViewById(R.id.rv_song_list);
        mSplMusic = (SwipeRefreshLayout) view.findViewById(R.id.spl_refresh_music);

        mIvImage = (ImageView) view.findViewById(R.id.widget_image);
        mTvContent = (TextView) view.findViewById(R.id.widget_content);
        mPbProgress = (ProgressBar) view.findViewById(R.id.widget_progress);
        mIvPre = (ImageView) view.findViewById(R.id.widget_pre);
        mIvPlay = (ImageView) view.findViewById(R.id.widget_play);
        mIvNext = (ImageView) view.findViewById(R.id.widget_next);
        mIntent = new Intent(mContext, PlayDetailActivity.class);
        initClick();
        return view;
    }

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showProgressDialog();
                    scanLocalMusic();

                } else {
                    showPermissionDialog();
                    //Toast.makeText(this, "本app需要此权限否则无法使用", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 向用户解释该权限的作用
     */
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
                Snackbar.make(mRvSongListView, "本app需要此权限否则无法使用,请前往系统设置开启", Snackbar.LENGTH_LONG)
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

    private void startUpdateSeekBarProgress(int currentProgress) {
        /*避免重复发送Message*/
        stopUpdateSeekBarProgree();
        mPbProgress.setProgress(currentProgress);
        mHandler.sendEmptyMessageDelayed(IS_PLAYING, 100);
    }

    private void stopUpdateSeekBarProgree() {
        mHandler.removeMessages(IS_PLAYING);
    }

    private void goToSetting() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", mContext.getPackageName(), null));
        startActivity(localIntent);
    }

    private void showShare(String info) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        //oks.setTitle(getString());
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitle("来自carter的分享");
        //oks.setTitleUrl("http://www.baidu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(info);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        //oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        // 启动分享GUI
        oks.show(mContext);
    }

    private void initClick() {

        mSplMusic.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showProgressDialog();
                scanLocalMusic();
            }
        });
        mIvPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPos > 0) {
                    currentPos--;
                    MusicInfo musicInfo = mMusicInfos.get(currentPos);
                    mTvContent.setText(musicInfo.getTitle());
                    mMusicInfoAdapter.setSelectItem(currentPos);
                    playMusic(musicInfo, 0);
                } else {
                    Toast.makeText(mContext, "没有上一曲了，请听其他的歌曲", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mIntent);
            }
        });
        mIvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPlaying) {
                    changeMusicState();
                    playMusic(mMusicInfoCurrent, 2);
                } else {
                    changeMusicState();
                    playMusic(mMusicInfoCurrent, 1);
                }

            }
        });
        mIvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPos < mMusicInfos.size()) {
                    currentPos++;
                    MusicInfo musicInfo = mMusicInfos.get(currentPos);
                    mTvContent.setText(musicInfo.getTitle());
                    mMusicInfoAdapter.setSelectItem(currentPos);
                    playMusic(musicInfo, 0);
                } else {
                    Toast.makeText(mContext, "没有下一曲了，请欣赏其他歌曲", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /*  private void playMusic(String path,int musicState) {
          //单例用于
          MusicEvent instance = MusicEvent.getInstance();
          instance.setPath(path);
          // 0 表示开始播放 1 暂停  2  继续
          instance.setMusicState(musicState);
          EventBus.getDefault().post(instance);
      }*/
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 开始播放音乐
     *
     * @param musicInfo  需要播放的音乐信息
     * @param musicState 指定播放状态
     */
    @SuppressLint("NewApi")
    private void playMusic(MusicInfo musicInfo, int musicState) {
        //单例用于
        MusicEvent instance = MusicEvent.getInstance();
        instance.setPath(musicInfo.getUrl());
        // 0 表示开始播放 1 暂停  2  继续
        instance.setMusicState(musicState);
        EventBus.getDefault().post(instance);
//        mPbProgress.setMin(0);
        switch (musicState) {
            case 0:
                mPbProgress.setMax(musicInfo.getDuration());
                startUpdateSeekBarProgress(0);
                break;
            case 1:
                mPbProgress.setMax(musicInfo.getDuration());
                pauseUpdateSeekBarProgress();
                break;
            case 2:
                mPbProgress.setMax(musicInfo.getDuration());
                startUpdateSeekBarProgress(mPauseProgress);
                break;
            default:
                break;
        }

        Log.d(TAG, "playMusic: " + musicInfo.getDuration());
    }

    private void pauseUpdateSeekBarProgress() {
        mPauseProgress = mProgress;
        Log.d(TAG, "pauseUpdateSeekBarProgress: " + mPauseProgress);
        Log.d(TAG, "pauseUpdateSeekBarProgress: " + mPbProgress.getProgress());
        mPbProgress.setProgress(mPauseProgress);
        mHandler.removeMessages(IS_PLAYING);

    }

    /**
     * 根据状态进行改变音乐是否播放的状态
     */
    private void changeMusicState() {
        if (!isPlaying) {
            mIvPlay.setImageResource(R.drawable.widget_pause_selector);
            isPlaying = true;
        } else {
            mIvPlay.setImageResource(R.drawable.widget_play_selector);
            isPlaying = false;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetHeadsetEvent(HeadsetEvent event) {
        int headsetState = event.getHeadsetState();
        Log.d(TAG, "onGetHeadsetEvent: " + headsetState);
        playMusic(mMusicInfoCurrent, headsetState);
        changeMusicState();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMusicEvent(MusicEvent event) {
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }

    class MusicInfoAdapter extends RecyclerView.Adapter<MusicInfoAdapter.ViewHolder> {

        private ArrayList<MusicInfo> musicInfos;

        private int currentMusicID = -1;

        private int defItem = -1;

        MusicInfoAdapter(ArrayList<MusicInfo> musicInfos) {
            this.musicInfos = musicInfos;
        }

        /**
         * 用于给正在播放的音乐做标记
         *
         * @param defItem
         */
        public void setSelectItem(int defItem) {
            this.defItem = defItem;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_music_info, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
            final MusicInfo musicInfo = musicInfos.get(position);
            holder.tvTitle.setText(musicInfo.getTitle());
            holder.tvArtist.setText(musicInfo.getAlbum());
            if (defItem != -1) {
                if (defItem == position) {
                    holder.ivPlayState.setImageResource(R.drawable.song_play_icon);
                    holder.ivPlayState.setVisibility(View.VISIBLE);
                } else {
                    holder.ivPlayState.setVisibility(View.GONE);
                }
            }
//            holder.ivMusic.setImageResource();
           /* Glide.with(mContext).load(MusicUtils.getArtwork(mContext, musicInfo.getId(),
                    musicInfo.getAlbum_id(), true, true)).into(holder.ivMusic);*/
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentPos = position;
                    setSelectItem(position);
                    if (isPlaying && currentMusicID == musicInfo.getId()) {
                        mContext.startActivity(mIntent);
                    } else if (!isPlaying) {
                        Toast.makeText(UiUtil.getContext(), "正在播放" + mMusicInfos.get(position).getTitle(), Toast
                                .LENGTH_SHORT).show();
                        mIvImage.setImageBitmap(MusicUtils.getArtwork(mContext, musicInfo.getId(),
                                musicInfo.getAlbum_id(), true, true));
                        mTvContent.setText(musicInfo.getTitle());
                        Log.d(TAG, "onClick: " + musicInfo.getUrl());
                        //playMusic(musicInfo.getUrl());
                        //playMusic(musicInfo);
                        playMusic(musicInfo, 0);
                        //更改状态
                        currentMusicID = musicInfo.getId();
                        mMusicInfoCurrent = musicInfo;
                        SpUtils.put(mContext, GlobalConstants.CURRENT_MUSIC, musicInfo.getUrl());
                        //更改音乐播放状态
                        changeMusicState();
                    } else if (isPlaying && currentMusicID != musicInfo.getId()) {
                        //playMusic(musicInfo.getUrl(),1);
                        playMusic(musicInfo, 0);
                        isPlaying = false;
                        //更改当前播放音乐的 id值
                        mTvContent.setText(musicInfo.getTitle());
                        currentMusicID = musicInfo.getId();
                        changeMusicState();
                    }
                }
            });
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showShare(musicInfo.getTitle());
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return musicInfos.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView tvTitle;
            TextView tvArtist;
            ImageView ivMusic;
            ImageView ivPlayState;

            ViewHolder(View itemView) {
                super(itemView);

                cardView = (CardView) itemView;
                tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                tvArtist = (TextView) itemView.findViewById(R.id.tv_artist);
                ivMusic = (ImageView) itemView.findViewById(R.id.iv_music_album);
                ivPlayState = (ImageView) itemView.findViewById(R.id.play_state);
            }
        }
    }
}
