package com.carter.graduation.design.music.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.carter.graduation.design.music.event.PositionEvent;

import org.greenrobot.eventbus.EventBus;

public class SeekPositionReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int seek = intent.getIntExtra("seek", 0);
        PositionEvent instance = PositionEvent.getInstance();
        instance.setPos(seek);
        EventBus.getDefault().post(instance);
    }
}
