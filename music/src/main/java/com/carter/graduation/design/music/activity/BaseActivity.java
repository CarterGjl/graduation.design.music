package com.carter.graduation.design.music.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.carter.graduation.design.music.utils.ActivityCollectorUtils;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtils.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtils.removeActivity(this);
    }
}
