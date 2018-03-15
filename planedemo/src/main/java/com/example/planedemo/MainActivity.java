package com.example.planedemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.sql.Time;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

    Random rand = new Random();
    //桌面的宽度
    private int tableWidth;
    private int tableHeight;
    //球拍的垂直位置
    private int racketY;
    private static final int RACKET_HEIGHT = 30;
    private static final int RACKET_WIDTH = 90;

    private static final int BALL_SIZE = 16;
    //小球纵向的速度
    private int ySpeed = 15;

    float ballX = rand.nextInt(200)+20;
    float ballY = rand.nextInt(10)+20;

    //球拍的水平位置
    double xyRate = rand.nextDouble() - 0.5;
    int xSpeed = (int) (ySpeed * xyRate * 2);
    int racketX = rand.nextInt(200);
    //游戏是否结束标志

    private boolean isLose = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager
                .LayoutParams.FLAG_FULLSCREEN);

        final GameView gameView = new GameView(this);
        setContentView(gameView);
        InputMethodManager inputMethodManager = (InputMethodManager) this.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        //同时再使用该方法之前，view需要获得焦点，可以通过requestFocus()方法来设定。
        gameView.requestFocus();
        inputMethodManager.showSoftInput(gameView, inputMethodManager.SHOW_FORCED);
        //获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        //获取屏幕宽和高
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;
        racketY = tableHeight - 80;
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x123){
                    gameView.invalidate();
                }
            }
        };
        gameView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()){
                    case KeyEvent.KEYCODE_A:
                        if (racketX>0){
                            racketX -=10;
                        }
                    break;
                    case KeyEvent.KEYCODE_D:
                        if (racketX<tableWidth-RACKET_WIDTH)
                        {
                            racketX +=10;
                        }
                        break;
                    default:
                    break;
                }
                gameView.invalidate();
                return true;
            }
        });
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (ballX<=0||ballX>=tableWidth-BALL_SIZE){
                    xSpeed = -xSpeed;
                }
                if (ballY>=racketY-BALL_SIZE&&(ballX<racketX||ballX>racketX+RACKET_WIDTH)){

                    timer.cancel();
                    isLose =true;
                } else if(ballY<=0||(ballY>=racketX-BALL_SIZE)
                        &&ballX>racketX&&ballX<=racketX+RACKET_WIDTH){
                    ySpeed=-ySpeed;
                }
                ballX += xSpeed;
                ballY += ySpeed;
                handler.sendEmptyMessage(0x123);
            }
        },0,100);
    }
    class GameView extends View{

        Paint mPaint = new Paint();
        public GameView(Context context) {
            super(context);
            setFocusable(true);
        }



        public GameView(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            mPaint.setStyle(Paint.Style.FILL);
            //设置抗锯齿
            mPaint.setAntiAlias(true);
            //如果游戏已经结束
            if (isLose){
                mPaint.setColor(Color.RED);
                mPaint.setTextSize(40);
                canvas.drawText("游戏已经结束",tableWidth/2-100,200,mPaint);

            }else {
                mPaint.setColor(Color.rgb(255,0,0));
                canvas.drawCircle(ballX,ballY,BALL_SIZE,mPaint);
                //设置颜色并且绘制球拍
                mPaint.setColor(Color.rgb(80,80,200));
                canvas.drawRect(racketX,racketY,racketX+RACKET_WIDTH,racketY+RACKET_HEIGHT,mPaint);
            }
            super.onDraw(canvas);

        }
    }
}
