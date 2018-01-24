package com.carter.graduation.design.music;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.carter.graduation.design.music.event.HeadsetEvent;

import org.greenrobot.eventbus.EventBus;

public class HeadsetReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED.equals(action)) {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            if (BluetoothProfile.STATE_DISCONNECTED == adapter.getProfileConnectionState
                    (BluetoothProfile.HEADSET)) {
                // TODO: 2018/1/23 蓝牙断开
            }
        } else if ("android.intent.action.HEADSET_PLUG".equals(action)) {
            if (intent.hasExtra("state")) {
                if (intent.getIntExtra("state", 0) == 0) {
                    Toast.makeText(context, "headset not connected", Toast.LENGTH_SHORT).show();
                    HeadsetEvent instance = HeadsetEvent.getInstance();
                    instance.setHeadsetState(1);
                    EventBus.getDefault().post(instance);
                } else if (intent.getIntExtra("state", 0) == 1) {
                    Toast.makeText(context, "headset connected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
