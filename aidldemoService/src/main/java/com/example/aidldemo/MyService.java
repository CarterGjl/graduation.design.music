package com.example.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    IMyAidlIml mIMyAidlIml = new IMyAidlIml();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIMyAidlIml;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
