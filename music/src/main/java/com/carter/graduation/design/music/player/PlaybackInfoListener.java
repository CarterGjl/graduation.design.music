package com.carter.graduation.design.music.player;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 音乐状态改变
 * Created by newthinkpad on 2018/1/25.
 */

public abstract class PlaybackInfoListener {
    public static String convertStateToString(@State int state) {
        String stateString;
        switch (state) {
            case State.COMPLETED:
                stateString = "COMPLETED";
                break;
            case State.INVALID:
                stateString = "INVALID";
                break;
            case State.PAUSED:
                stateString = "PAUSED";
                break;
            case State.PLAYING:
                stateString = "PLAYING";
                break;
            case State.RESET:
                stateString = "RESET";
                break;
            default:
                stateString = "N/A";
        }
        return stateString;
    }

    public void onDurationChanged(int duration) {
    }

    public void onPositionChanged(int position) {
    }

    public void onStateChanged(@State int state) {
    }

    public void onPlaybackCompleted() {
    }

    @IntDef({State.INVALID, State.PLAYING, State.PAUSED, State.RESET, State.COMPLETED})
    @Retention(RetentionPolicy.SOURCE)
    @interface State {
        int INVALID = -1;
        int PLAYING = 0;
        int PAUSED = 1;
        int RESET = 2;
        int COMPLETED = 3;
    }
}
