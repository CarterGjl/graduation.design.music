package com.carter.graduation.design.music.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;

import com.carter.graduation.design.music.R;

public class SplashActivity extends Activity {

    private static final int SPLASH_DELAY_MILLS = 800;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        /*UiUtil.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        },SPLASH_DELAY_MILLS);*/
        if (Build.VERSION.SDK_INT >= 22) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        LinearLayout llSplash;
        llSplash = findViewById(R.id.ll_splash);
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation
                .RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(SPLASH_DELAY_MILLS);
        rotateAnimation.setFillAfter(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation
                .RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(SPLASH_DELAY_MILLS);
        scaleAnimation.setFillAfter(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(SPLASH_DELAY_MILLS);
        alphaAnimation.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);

        llSplash.startAnimation(animationSet);
        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goHome();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void goHome() {

        Intent intent = new Intent(SplashActivity.this, HomeDetailActivity.class);
        startActivity(intent);
        finish();
    }
}
