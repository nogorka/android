package com.example.recyclelist;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ColorViewHolder extends RecyclerView.ViewHolder {
    TextView tv;
    Context context;

    public ColorViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.color);
        context = itemView.getContext();
    }

    void bindTo(Integer color) {
        tv.setBackgroundColor(color);
        tv.setText("color: " + matchColor(color));
    }

    String matchColor(int colorValue) {
        ArrayList<Integer> keyVals = MainActivity.colorsValues;
        for (int i = 0; i < keyVals.size(); i++) {
            if (keyVals.get(i) == colorValue) {
                return MainActivity.colorsNames.get(i);
            }
        }
        return "";
    }
}