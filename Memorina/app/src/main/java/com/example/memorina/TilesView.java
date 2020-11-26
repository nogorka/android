package com.example.memorina;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class Card {
    Paint p = new Paint();
    int color, backColor = Color.rgb(192, 192, 192);
    boolean isOpen = false; // цвет карты
    float x, y, width, height;
    int outline = 10;

    public Card(int color) {
        this.color = color;
    }

    public void reshapeCard(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw(Canvas c) { // нарисовать карту в виде цветного прямоугольника
        if (isOpen) {
            p.setColor(color);
        } else
            p.setColor(backColor);
        c.drawRect(x + outline, y + outline, x + width - outline, y + height - outline, p);
    }

    public boolean flip(float touch_x, float touch_y) {
        if (touch_x >= x && touch_x <= x + width && touch_y >= y && touch_y <= y + height) {
            isOpen = !isOpen;
            return true;
        }
        return false;
    }

}

public class TilesView extends View {

    int row = 4, col = 4;

    final int PAUSE_LENGTH = 2; // пауза для запоминания карт в секундах
    boolean isOnPauseNow = false;
    int openedCard = 0;  // число открытых карт

    Card[] openedCards = new Card[2];
    int solvedCards = 0;

    Card[][] cards = new Card[row][col];

    ArrayList<Integer> colors = new ArrayList<Integer>(Arrays.asList(
            Color.YELLOW, Color.GREEN,
            Color.BLUE, Color.BLACK,
            Color.MAGENTA, Color.CYAN,
            Color.RED, Color.WHITE
    ));

    public TilesView(Context context) {
        super(context);
    }

    public TilesView(Context context, @Nullable AttributeSet attrs) {
        super(context);
        int it = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                int color;
                if (it >= row * col / 2) {
                    int rnd = new Random().nextInt(colors.size());
                    color = colors.get(rnd);
                    colors.remove(rnd);

                } else {
                    color = colors.get(it);
                }
                cards[i][j] = new Card(color);
                it++;

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int t_height = canvas.getHeight() / row;
        int t_width = canvas.getWidth() / col;

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                int left = j * t_width;
                int top = i * t_height;


                cards[i][j].reshapeCard(left, top, t_width, t_height);
                cards[i][j].draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN && !isOnPauseNow) {

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {

                    if (openedCard == 0) {
                        if (cards[i][j].flip(x, y)) {

                            openedCard++;
                            openedCards[0] = cards[i][j];
                            invalidate();
                            return true;
                        }
                    }

                    if (openedCard == 1) {

                        if (cards[i][j].flip(x, y)) {
                            openedCard++;
                            openedCards[1] = cards[i][j];
                            Log.d("mytag", "card : " + " - " + openedCards[0].color);
                            Log.d("mytag", "card : " + " - " + openedCards[1].color);

                            if (openedCards[0].color == openedCards[1].color) {

                                openedCards[0].backColor = openedCards[0].color;
                                openedCards[1].backColor = openedCards[1].color;
                                solvedCards += 2;
                            }
                            invalidate();
                            PauseTask task = new PauseTask();
                            task.execute(PAUSE_LENGTH);
                            isOnPauseNow = true;
                            return true;
                        }
                    }

                    if (solvedCards == row * col) {
                        Toast toast = Toast.makeText(getContext(),
                                "Wow, you managed that", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        }
        return true;
    }

    public void newGame() {
        // TODO запуск новой игры
    }

    class PauseTask extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... integers) {
            Log.d("mytag", "Pause started");
            try {
                Thread.sleep(integers[0] * 1000); // передаём число секунд ожидания
            } catch (InterruptedException e) {
            }
            Log.d("mytag", "Pause finished");
            return null;
        }

        // после паузы, перевернуть все карты обратно


        @Override
        protected void onPostExecute(Void aVoid) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (cards[i][j].isOpen) {
                        cards[i][j].isOpen = false;
                    }
                }
            }
            openedCard = 0;
            isOnPauseNow = false;
            invalidate();
        }
    }
}