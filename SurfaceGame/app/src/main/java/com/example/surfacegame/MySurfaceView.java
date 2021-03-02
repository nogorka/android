package com.example.surfacegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    DrawThread thread;
    float left, top, right, bottom;
    boolean flag = false;

    ArrayList<Integer> colors = new ArrayList<Integer>(Arrays.asList(
            Color.rgb(192,192,192), Color.rgb(0,128,0),
            Color.rgb(128,0,128), Color.rgb(0,128,128),
            Color.rgb(255,0,255), Color.rgb(0,0,255),
            Color.rgb(150,50,255)
            ));

    RectF myRect = new RectF();

    class DrawThread extends Thread {
        float x = 300, y = 300 ; //for first ball
        float x1 = 300, y1 = 300; //for sec ball

        int rad = 30;
        Random r = new Random();
        Paint p = new Paint(); // for first ball
        Paint p2 = new Paint(); // for sec ball
        Paint p3 = new Paint();// rect

        boolean runFlag = true;
        // в конструкторе нужно передать holder для дальнейшего доступа к канве
        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }


        SurfaceHolder holder;


        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            super.run();

            left = 400; top = 400; right = 500; bottom = 500;

            p.setColor(Color.rgb(79,144,242)); // blue
            p2.setColor(Color.rgb(32,162,28)); // green
            p3.setColor(Color.BLACK);

            int dist = 100;

            x+= r.nextFloat() * dist - 5;
            y+= r.nextFloat() * dist - 5;

            x1 += r.nextFloat() * dist - 5;
            y1 += r.nextFloat() * dist - 5;

            float dx = 50;
            float dy = 40;

            float dx2 = -50;
            float dy2 = -40;

            // выполняем цикл (рисуем кадры) пока флаг включен
            while (runFlag) {
                Canvas c = holder.lockCanvas();
                // если успешно захватили канву
                if (c != null) {

                    c.drawColor(Color.rgb(202,145,145)); // display color
                    myRect.set(left, top,  right, bottom);
                    c.drawRect(myRect, p3);

                    //change coord
                    x += dx;
                    y += dy;

                    x1 += dx2;
                    y1 += dy2;

                    // случайные блуждания - сдвигаем координаты шарика в случайную сторону

                    if (x >= c.getWidth() - rad  || x1 >= c.getWidth() - rad ) {
                        dx *= -1;
                        dx2 *= -1;
                        dist -=100;

                    } else if (y >= c.getHeight() - rad || y1 >= c.getHeight() - rad ) {
                        dy *= -1;
                        dy2 *= -1;
                        dist +=100;

                    } else if (x < rad || x1 < rad){
                        dx *= -1;
                        dx2 *= -1;
                        dist -=100;

                    } else if (y < rad || y1 < rad) {
                        dy *= -1;
                        dy2 *= -1;
                        dist -=100;

                    }
                    else if (x1 + rad == x + rad || y1 + rad == y + rad ) {
                        dx *= -1;
                        dx2 *= -1;
                        dy *= -1;
                        dy2 *= -1;
                        dist +=100;
                    }
                    else if ((x >= myRect.left && x <= myRect.right) && (y >= myRect.top && y <= myRect.bottom) ) {
                        dx *= -1;
                        dy *= -1;
                        dist +=100;
                        int random = (int) (r.nextFloat() * colors.size());
                        int color = colors.get(random);
                        p.setColor(color);
                    }
                    else if ((x1 >= myRect.left && x1 <= myRect.right) && (y1 >= myRect.top && y1 <= myRect.bottom)) {
                        dx2 *= -1;
                        dy *= -1;
                        dist +=100;
                        int random = (int) (r.nextFloat() * colors.size());
                        int color = colors.get(random);
                        p2.setColor(color);
                    }

                    c.drawCircle(x,y,rad,p);
                    c.drawCircle(x1,y1,rad,p2);


                    if (p.getColor() == p2.getColor()){
                        Paint pt = new Paint();
                        pt.setColor(Color.CYAN);
                        pt.setTextSize(50);
                        c.drawText("Same Color! You Won!!", 300, 200, pt);
                        runFlag=false;
                    }

                    holder.unlockCanvasAndPost(c);

                    // нужна пауза на каждом кадре
                    try {
                        Thread.sleep(100); }
                    catch (InterruptedException e) {}
                }
            }

        }


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                flag = true;
                if (flag) {

                    final int x_new = (int) event.getX();
                    final int y_new = (int) event.getY();

                    left = x_new - 100;
                    right = x_new + 100;
                    top = y_new - 100 ;
                    bottom = y_new + 100;

                }
                return true;
            case MotionEvent.ACTION_UP:
                flag = false;
                return true;
        }

        return true;

    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new DrawThread(holder);
        thread.start();
        // убеждаемся, что поток запускается
        Log.d("mytag", "DrawThread is running");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // при изменении конфигурации поверхности поток нужно перезапустить
        thread.runFlag = false;
        thread = new DrawThread(holder);
        thread.start();
    }

    // поверхность уничтожается - поток останавливаем
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.runFlag = false;
    }
}