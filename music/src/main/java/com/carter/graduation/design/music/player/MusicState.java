package com.carter.graduation.design.music.player;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class MusicState {
    @IntDef({State.PLAYING, State.PAUSED, State.CONTINUE_PLAYING, State.COMPLETED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        int PLAYING = 0;
        int PAUSED = 1;
        int CONTINUE_PLAYING = 2;
        int COMPLETED = 3;
    }
}
