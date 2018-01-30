package com.carter.graduation.design.music.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.carter.graduation.design.music.R;
import com.carter.graduation.design.music.TimerExit;

/**
 * Created by newthinkpad on 2018/1/30.
 */

public class TimingFragment extends DialogFragment implements View.OnClickListener {
    private Context mContext;

    private TextView timing10, timing20, timing30, timing45, timing60, timing90;
    private TimerExit mTimerExit;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //设置无标题
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_timing, container, false);
        timing10 = (TextView) view.findViewById(R.id.timing_10min);
        timing20 = (TextView) view.findViewById(R.id.timing_20min);
        timing30 = (TextView) view.findViewById(R.id.timing_30min);
        timing45 = (TextView) view.findViewById(R.id.timing_45min);
        timing60 = (TextView) view.findViewById(R.id.timing_60min);
        timing90 = (TextView) view.findViewById(R.id.timing_90min);

        timing10.setOnClickListener(this);
        timing20.setOnClickListener(this);
        timing30.setOnClickListener(this);
        timing45.setOnClickListener(this);
        timing60.setOnClickListener(this);
        timing90.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.71);
        int dialogWidth = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.79);
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(dialogWidth, dialogHeight);
        }
//        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Override
    public void onClick(View v) {
        mTimerExit = TimerExit.getInstance();
        switch (v.getId()) {

            case R.id.timing_10min:
                mTimerExit.cancelTask();
                mTimerExit.startTask(1);
                Toast.makeText(mContext, "将在10分钟后停止播放", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.timing_20min:
                mTimerExit.cancelTask();
                mTimerExit.startTask(20);
                Toast.makeText(mContext, "将在20分钟后停止播放", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.timing_30min:
                mTimerExit.cancelTask();
                mTimerExit.startTask(30);
                Toast.makeText(mContext, "将在30分钟后停止播放", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.timing_45min:
                mTimerExit.cancelTask();
                mTimerExit.startTask(45);
                Toast.makeText(mContext, "将在45分钟后停止播放", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.timing_60min:
                mTimerExit.cancelTask();
                mTimerExit.startTask(60);
                Toast.makeText(mContext, "将在60分钟后停止播放", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
            case R.id.timing_90min:
                mTimerExit.cancelTask();
                mTimerExit.startTask(90);
                Toast.makeText(mContext, "将在90分钟后停止播放", Toast.LENGTH_SHORT).show();
                dismiss();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
