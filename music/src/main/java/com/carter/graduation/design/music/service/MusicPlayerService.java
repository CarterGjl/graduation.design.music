package com.carter.graduation.design.music.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.activity.HomeDetailActivity;
import com.carter.graduation.design.music.event.MusicEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MusicPlayerService extends Service implements Runnable {

    private static final String TAG = "MusicPlayerService";
    //通知id
    private static final int NOTIFICATION = 1;
    private static final int PLAYING = 0;
    private static final int PAUSE_PLAYING = 1;
    private static final int CONTINUE_PLAYING = 2;
    private static MediaPlayer mMediaPlayer = null;
    /* @SuppressLint("HandlerLeak")
     private Handler mHandler = new Handler(){
         @Override
         public void handleMessage(Message msg) {
             switch (msg.what){
                 case 0:
                     int currentPosition = (int) msg.obj;
                     MusicEvent musicEvent = MusicEvent.getInstance();
                     musicEvent.setCurrentPosition(currentPosition);
                     EventBus.getDefault().post(musicEvent);
                 break;
                 default:
                 break;
             }
             super.handleMessage(msg);
         }
     };*/
    //当前播放歌曲
    private static int currentPos;
    private Timer mTimer = new Timer();
    /**
     * 用于获取当前的播放的位置
     */
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (mMediaPlayer == null)
                return;
            if (mMediaPlayer.isPlaying()) {
                int currentPosition = mMediaPlayer.getCurrentPosition();
                Log.d(TAG, "run: " + currentPosition);
                MusicEvent musicEvent = MusicEvent.getInstance();
                //musicEvent.setCurrentPosition(currentPosition);
                EventBus.getDefault().post(musicEvent);
            }
        }
    };
    //当前的进度条
    private int currentPosition = 0;
    private MusicBinder mBinder = new MusicBinder(this);

    public MusicPlayerService() {

    }

    /**
     * 获取当前播放的音乐
     *
     * @return 当前播放的位置
     */
    public static int getCurrentMusicPos() {
        return currentPos;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        Log.d(TAG, "onCreate: ");


        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                //重置进度条
                MusicEvent instance = MusicEvent.getInstance();
                instance.setResetProgress(0);
                EventBus.getDefault().post(instance);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放音乐
     */
    private void player(String url, int musicState) {
        Log.i(TAG, "player: ");

        switch (musicState) {
            case PLAYING:
                playingMusic(url);
                break;
            case PAUSE_PLAYING:
                pausePlaying();
                break;
            case CONTINUE_PLAYING:
                continuePlaying();
                break;
            default:
                break;
        }
       /* if (musicState == 0) {

        } else if (musicState == 1) {

        } else if (musicState == 2) {

        }*/
    }

    private void continuePlaying() {
        ///继续播放
        mMediaPlayer.start();
    }

    private void pausePlaying() {
        mMediaPlayer.pause();
    }

    private void playingMusic(String url) {
        Log.i(TAG, "onStartCommand: " + Thread.currentThread().getName());
        //开始播放  无论是否头正在播放的音乐重置
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            continuePlaying();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
       /* mMSG = intent.getStringExtra("MSG");
        Log.i(TAG, "onStartCommand: " + mMSG);
        if (mMSG.equals("0")) {

            String url = intent.getStringExtra("url");
            intent.getIntExtra("currentMusicPos", 0);

            Log.i(TAG, "onStartCommand: " + Thread.currentThread().getName());
            player(url);
        } else if (mMSG.equals("1")) {
            mMediaPlayer.pause();
        } else if (mMSG.equals("2")) {
            mMediaPlayer.start();
        }*/
        Notification notification;
//        mNm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent musicIntent = new Intent(this, HomeDetailActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, musicIntent, 0);

        notification = new Notification.Builder(this)
                .setContentTitle("music running")
                .setContentText("别摸我 摸我咬你 (￢_￢)智商三岁")
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
                .setSmallIcon(R.drawable.small_icon)
                .setContentIntent(pi)
                .setOngoing(true)
                .build();

        startForeground(NOTIFICATION, notification);
//        mTimer.schedule(mTimerTask,0,1000);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void run() {
        Log.i(TAG, "run: " + Thread.currentThread().getName());
        int totalDuration = mMediaPlayer.getDuration();
        while (mMediaPlayer != null && currentPosition < totalDuration) {
            try {
                Thread.sleep(1000);
                if (mMediaPlayer != null) {
                    currentPosition = mMediaPlayer.getCurrentPosition();
                    MusicEvent event = MusicEvent.getInstance();
//                    event.setCurrentPosition(currentPosition);
                    EventBus.getDefault().post(event);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMusicEvent(MusicEvent event) {
        String path = event.getPath();
        int musicState = event.getMusicState();
        player(path, musicState);
//        new Thread(this).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        //关闭线程
        Thread.currentThread().interrupt();
        stopForeground(true);
    }

    /**
     * 获取当前播放的位置
     *
     * @return 返回当前的播放位置
     */
    public int getCurrentPosition() {
        if (mMediaPlayer != null) {
            int totalDuration = mMediaPlayer.getDuration();
            if (currentPosition < totalDuration) {
                currentPosition = mMediaPlayer.getCurrentPosition();
            }
        }
        return currentPosition;
    }

    //获取总时长
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public class MusicBinder extends Binder {
        //暂时不改  以后根据需求来决定
        private MusicPlayerService mService;

        private MusicBinder(MusicPlayerService service) {
            this.mService = service;
        }

        public int getCurrentPosition() {
            return mService.getCurrentPosition();
        }

        public int getDuration() {
            return mService.getDuration();
        }
    }
}
