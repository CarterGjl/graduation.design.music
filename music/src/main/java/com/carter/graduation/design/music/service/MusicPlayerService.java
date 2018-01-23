package com.carter.graduation.design.music.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.activity.HomeDetailActivity;
import com.carter.graduation.design.music.event.MusicEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;


public class MusicPlayerService extends Service implements Runnable {

    private static final String TAG = "MusicPlayerService";
    private static final int NOTIFICATION = 1;
    private static MediaPlayer mMediaPlayer = null;

    //当前播放歌曲
    private static int currentPos;
    //当前的进度条
    private int currentPosition = 0;
    private String mMSG;
    private MusicBinder mBinder = new MusicBinder(this);
    private NotificationManager mNm;

    public MusicPlayerService() {

    }

    public static MediaPlayer getmMediaPlayer() {
        return mMediaPlayer;
    }

    /**
     * 获取当前播放的音乐
     *
     * @return
     */
    public static int getCurrentMusicPos() {
        return currentPos;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        Toast.makeText(this, "hhahdhfsaf", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onCreate: ");
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // TODO: 2018/1/22 歌曲完成后需要做的事情  更改歌曲状态
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onStartCommand: ");
        if (intent != null) {
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放音乐
     */
    private void player(String url) {
        Log.i(TAG, "player: ");
        //判断是否正在播放其他的音乐 暂停并重置
       /* if (mMediaPlayer.isPlaying()) {
            Log.i(TAG, "player: isrunning");
            //暂停
            mMediaPlayer.pause();
            mMediaPlayer.reset();
        }*/
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            new Thread(this).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        mMSG = intent.getStringExtra("MSG");
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
        }
        Notification notification = null;
        mNm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
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
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMusicEvent(MusicEvent event) {
        String path = event.getPath();
        player(path);
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
     * @return
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

    /* public String timeFormat(int time){
         time /= 1000;
         int minute = time / 60;
         int hour = minute / 60;
         int second = time%60;
         return String.format("%02d:%02d",minute,second);
     }*/
    public class MusicBinder extends Binder {
        private MusicPlayerService mService;

        public MusicBinder(MusicPlayerService service) {
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
