package com.example.surfacegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder holder;
    int backgroundColor = Color.WHITE;
    DrawThread thread;

    Ball ball1;
    Ball ball2;

    MyRect rect;

    int[] pallete = {Color.RED, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.BLUE, Color.BLACK, Color.YELLOW};

    int getRandomInt(int max) {
        return (int) (Math.random() * max);
    }

    int pow(int n) {
        return n * n;
    }


    class DrawThread extends Thread {
        boolean runFlag = true;

        // в конструкторе нужно передать holder для дальнейшего доступа к канве
        public DrawThread(SurfaceHolder holder) {
            this.holder = holder;
        }

        SurfaceHolder holder;

        @Override
        public void run() {
            super.run();
            rect.rect.set(1080/2-50, 1548/2-50, 1080/2+50, 1548/2+50);

            while (runFlag) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(backgroundColor);

                    rect.draw(canvas);

                    ball1.OnCollisionBorder(canvas);
                    ball2.OnCollisionBorder(canvas);

                    ball1.OnCollisionBall(ball2);

                    ball1.OnCollisionRect(rect);
                    ball2.OnCollisionRect(rect);

                    ball1.move(
                            canvas,
                            ball1.x + ball1.speedX,
                            ball1.y + ball1.speedY
                    );
                    ball2.move(canvas,
                            ball2.x + ball2.speedX,
                            ball2.y + ball2.speedY
                    );

                }
                holder.unlockCanvasAndPost(canvas);

                // нужна пауза на каждом кадре
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    //todo onTouchEvent


    public TestSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // этот класс является обработчиком событий с поверхностью
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        ball1 = new Ball(getRandomInt(1081), getRandomInt(1081), 30);
        ball2 = new Ball(getRandomInt(1081), getRandomInt(1081), 30);
        rect = new MyRect();
        // запустить поток отрисовки
        thread = new DrawThread(holder);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int i, int i1, int i2) {
        thread.runFlag = false;
        thread = new DrawThread(holder);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.runFlag = false;
    }

    class MyRect {
        Rect rect;
        Paint paint;

        MyRect() {
            rect = new Rect();

            paint = new Paint();
            paint.setColor(Color.BLACK);
        }

        public int getX() {
            return rect.right - rect.left;
        }

        public int getY() {
            return rect.bottom - rect.top;
        }

        public void draw(Canvas canvas) {
            canvas.drawRect(this.rect, this.paint);
        }
    }


    class Ball {

        int x = 100, y = 100;
        int speedX = 50, speedY = 50;

        int radius = 1;

        int colorID;
        Paint paint;

        public Ball(int _x, int _y, int r) {
            radius = r;
            x = _x;
            y = _y;
            colorID = getRandomInt(7);
            changeColor();
        }

        private void draw(Canvas canvas) {
            canvas.drawCircle(this.x, this.y, this.radius, this.paint);
        }

        private void changeColor() {
            Paint p = new Paint();
            if (colorID + 1 >= pallete.length)
                colorID = 0;
            else
                colorID++;

            p.setColor(pallete[colorID]);
            this.paint = p;
        }

        public void move(Canvas canvas, int _x, int _y) {
            this.x = _x;
            this.y = _y;

            this.draw(canvas);
        }

        private void invertSpeedX() {
            this.speedX *= -1;
        }

        private void invertSpeedY() {
            this.speedY *= -1;
        }

        public void OnCollisionBall(Ball b) {
            int dist = pow(this.x - b.x) + pow(this.y - b.y);
            if (dist <= pow(this.radius + b.radius)) {

                this.changeColor();
                this.invertSpeedX();
                this.invertSpeedY();

                b.changeColor();
                b.invertSpeedX();
                b.invertSpeedY();

            }
        }

        public void OnCollisionBorder(Canvas canvas) {
            int width = canvas.getWidth();
            int height = canvas.getHeight();

            if (width <= this.x + this.radius || 0 >= this.x - this.radius) {
                invertSpeedX();
                changeColor();
            }
            if (height <= this.y + this.radius || 0 >= this.y - this.radius) {
                invertSpeedY();
                changeColor();
            }
        }

        public void OnCollisionRect(MyRect rect) {

            if (rect.rect.intersects(x - radius, y - radius, x + radius, y + radius)) {
                invertSpeedX();
                invertSpeedY();
                changeColor();
            }
        }
    }
}



