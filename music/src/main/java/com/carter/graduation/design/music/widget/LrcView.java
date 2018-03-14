package com.carter.graduation.design.music.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.carter.graduation.design.music.info.LrcInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carter on 2018/3/13
 */

public class LrcView extends android.support.v7.widget.AppCompatTextView {

    private float width;//歌词视图的宽度
    private float height;
    private Paint currentPaint;
    private Paint notCurrentPaint;
    private float textHeight; //文本高度
    private float textSize = 18; //文本大小
    private int index;

    private List<LrcInfo> mLrcInfos = new ArrayList<>();

    public void setLrcInfos(List<LrcInfo> lrcInfos) {
        mLrcInfos = lrcInfos;
    }

    public LrcView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setFocusable(true);  //设置可对焦

        //高亮部分
        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);//设置抗锯齿  让文字更美观
        currentPaint.setTextAlign(Paint.Align.CENTER);

        //非高亮部分
        notCurrentPaint = new Paint();
        notCurrentPaint.setAntiAlias(true);
        notCurrentPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 绘制歌词
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) {
            return;
        }
        currentPaint.setColor(Color.argb(210, 251, 248, 29));
        notCurrentPaint.setColor(Color.argb(140, 255, 255, 255));

        currentPaint.setTextSize(24);
        currentPaint.setTypeface(Typeface.DEFAULT);

        notCurrentPaint.setTextSize(textSize);
        notCurrentPaint.setTypeface(Typeface.DEFAULT);

        try {
            setText("");
            canvas.drawText(mLrcInfos.get(index).getLrcStr(), width / 2, height / 2, currentPaint);

            float tempY = height / 2;
            //画出本句之前的句子
            for (int i = index - 1; i > 0; i--) {
                //向上推移
                tempY = tempY - textHeight;
                canvas.drawText(mLrcInfos.get(i).getLrcStr(), width / 2, tempY, notCurrentPaint);
            }
            tempY = height / 2;
            //画出本句之后的句子
            for (int i = index + 1; i < mLrcInfos.size(); i++) {
                //向下推移
                tempY = tempY+textHeight;
                canvas.drawText(mLrcInfos.get(i).getLrcStr(),width/2,tempY,notCurrentPaint);
            }
        } catch (Exception e) {
            setText("木有歌词，赶紧下载去把");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    public LrcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LrcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
