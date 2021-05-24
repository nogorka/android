package com.example.recyclelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView rview;

    public static ArrayList<Integer> colorsValues = new ArrayList<>();
    public static ArrayList<String> colorsNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] colors_raw = getResources().getIntArray(R.array.color_values);
        for (int c : colors_raw) {
            colorsValues.add(c);
        }

        colorsNames = new ArrayList<String>(
                Arrays.asList(getResources().getStringArray(R.array.color_names)));

        rview = findViewById(R.id.rview);

        ColorAdapter adapter = new ColorAdapter(getLayoutInflater());
        adapter.submitList(colorsValues);

        rview.setLayoutManager(new LinearLayoutManager(this));
        // задаём оформление
        rview.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rview.setAdapter(adapter);

    }

}