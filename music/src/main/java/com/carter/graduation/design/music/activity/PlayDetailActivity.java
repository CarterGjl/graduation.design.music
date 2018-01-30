package com.carter.graduation.design.music.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.event.DurationEvent;
import com.carter.graduation.design.music.event.MusicStateEvent;
import com.carter.graduation.design.music.event.SeekBarEvent;
import com.carter.graduation.design.music.info.MusicInfo;
import com.carter.graduation.design.music.player.MusicState;
import com.carter.graduation.design.music.utils.MusicUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class PlayDetailActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "PlayDetailActivity";
    private static int currentPlayingPos = 0;
    private SeekBar mSbMusic;
    private TextView mTvTitle;
    private TextView mTvArtist;
    private TextView mTvCurrentTime;
    private TextView mTvTotalTime;
    private ImageView mIvPlayOrPause;


    private boolean mUserIsSeeking = false;
    private boolean mIsPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_play_detail);
        initView();
    }

    @Override
    protected void onStart() {
        getSomething();
        super.onStart();
    }

    private void getSomething() {
        Intent intent = getIntent();
        int duration = intent.getIntExtra("duration", 0);
        MusicInfo musicInfo = (MusicInfo) intent.getParcelableExtra("musicInfo");
        Log.d(TAG, "onStart: " + duration);
        mTvTotalTime.setText(MusicUtils.formatTime(duration));
        mTvCurrentTime.setText(MusicUtils.formatTime(currentPlayingPos));
        mTvArtist.setText(musicInfo.getAlbum());
        Log.d(TAG, "onStart: " + musicInfo.getTitle());
        Log.d(TAG, "onStart: " + musicInfo.getAlbum());
        mTvTitle.setText(musicInfo.getTitle());
        mSbMusic.setMax(duration);
        mSbMusic.setProgress(currentPlayingPos);
        mIsPlaying = intent.getBooleanExtra("isPlaying", false);
        if (mIsPlaying) {
            mIvPlayOrPause.setImageResource(R.drawable.widget_pause_selector);
        } else {
            mIvPlayOrPause.setImageResource(R.drawable.widget_play_selector);
        }
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvArtist = (TextView) findViewById(R.id.tv_artist);
        mTvCurrentTime = (TextView) findViewById(R.id.tvCurrentTime);
        mTvTotalTime = (TextView) findViewById(R.id.tvTotalTime);
        ImageView ivPre = (ImageView) findViewById(R.id.iv_pre);
        mIvPlayOrPause = (ImageView) findViewById(R.id.iv_play_or_pause);
        ImageView ivNext = (ImageView) findViewById(R.id.iv_next);
        mSbMusic = (SeekBar) findViewById(R.id.musicSeekBar);

        ivPre.setOnClickListener(this);
        mIvPlayOrPause.setOnClickListener(this);
        ivNext.setOnClickListener(this);
        mSbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int userSelectedPosition = 0;


            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    userSelectedPosition = progress;
                    //seekTo();
                    Log.d(TAG, "onProgressChanged: " + userSelectedPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mUserIsSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                    mUserIsSeeking = false;
                Intent intent = new Intent("com.carter.graduation.design.music");
                intent.putExtra("seek", userSelectedPosition);
                Log.d(TAG, "onStopTrackingTouch: " + userSelectedPosition);
                sendBroadcast(intent);
            }

          /*  private void seekTo() {
                SeekBarEvent instance = SeekBarEvent.getInstance();
                instance.setUserSelectedPosition(userSelectedPosition);
                EventBus.getDefault().post(instance);
            }*/
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_pre:
                //上一曲
                break;
            case R.id.iv_play_or_pause:
                //播放
                break;
            case R.id.iv_next:
                //下一曲
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMusicFinished(MusicStateEvent stateEvent) {
        int state = stateEvent.getState();
        switch (state) {

            case MusicState.State.CONTINUE_PLAYING:
                break;
            case MusicState.State.PAUSED:
                break;
            case MusicState.State.PLAYING:

                break;
            case MusicState.State.COMPLETED:
                mIsPlaying = false;
                mIvPlayOrPause.setImageResource(R.drawable.widget_play_selector);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetSeekBarEvent(SeekBarEvent instance) {

        int seekBarPosition = instance.getSeekBarPosition();
        Log.d(TAG, "onGetSeekBarEvent: " + seekBarPosition);
        mTvCurrentTime.setText(MusicUtils.formatTime(seekBarPosition));
        currentPlayingPos = seekBarPosition;
        if (!mUserIsSeeking) {
            mSbMusic.setProgress(seekBarPosition);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetDurationEvent(DurationEvent durationEvent) {

    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
