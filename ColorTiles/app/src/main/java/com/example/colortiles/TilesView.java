package com.example.colortiles;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

class Tile {
    int color;
    int left;
    int right;
    int bottom;
    int top;

    Tile(int l, int t, int r, int b, int c) {
        color = c;
        left = l;
        right = r;
        bottom = b;
        top = t;
    }

    int getColor() {
        return color;
    }

    void setColor(int c) {
        color = c;
    }
}

public class TilesView extends View {

    boolean flag = true;
     int col = 2, row = 2;
    int outline = 10;
    int width, height;

    Tile[][] tiles = new Tile[col][row];


    int darkColor = Color.BLUE, lightColor = Color.YELLOW;

    public TilesView(Context context, int row, int col) {
        super(context);
    }

    public TilesView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        width = canvas.getWidth();
        height = canvas.getHeight();

        int t_width = width / col;
        int t_height = height / row;

        Paint light = new Paint();
        light.setColor(lightColor);
        Paint dark = new Paint();
        dark.setColor(darkColor);

        light.setStyle(Paint.Style.FILL);
        dark.setStyle(Paint.Style.FILL);


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                int left = j * t_width;
                int top = i * t_height;
                int right = left + t_width;
                int bottom = top + t_height;

                Rect tile = new Rect();
                tile.set(left + outline, top + outline, right - outline, bottom - outline);

                int color;
                if (flag) {
                    if (Math.random() > 0.5) {
                        canvas.drawRect(tile, light);
                        color = 1;
                    } else {
                        canvas.drawRect(tile, dark);
                        color = 0;
                    }
                    tiles[i][j] = new Tile(left, top, right, bottom, color);

                } else {
                    color = tiles[i][j].getColor();
                    if (color == 0) {
                        canvas.drawRect(tile, light);
                        color = 1;
                    } else {
                        canvas.drawRect(tile, dark);
                        color = 0;
                    }
                }
            }
        }
        if (flag) flag = false;
        super.onDraw(canvas); // рисуем на канвасе обращаясь к родительскому классу
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {

                    if (tiles[i][j].left < x && tiles[i][j].right > x &&
                            tiles[i][j].top < y && tiles[i][j].bottom > y) {

                        int k = i, m = j;

                        for (int ii = 0; ii < row; ii++) {
                            for (int jj = 0; jj < col; jj++) {

                                if (ii == k || jj == m) {
                                    if (tiles[ii][jj].getColor() == 1)
                                        tiles[ii][jj].setColor(0);
                                    else
                                        tiles[ii][jj].setColor(1);
                                }
                            }
                        }
                        break;
                    }
                }
            }
            int sum = 0;

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    sum += tiles[i][j].getColor();
                }
            }
            int color = 0;
            if (sum == 0 || sum == col * row) {

                Toast toast = Toast.makeText(getContext(),
                        "Wow, you managed that", Toast.LENGTH_SHORT);
                toast.show();
            }

        }
        invalidate();
        return true;
    }

}
