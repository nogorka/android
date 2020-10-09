package com.example.movieoffer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView movieTV;

    ArrayList<String> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.movieList)));
        movieTV = findViewById(R.id.movieNameTV);
    }


    public void onClick(View v) {

        if (movies.size() != 0) {
            int randomId = (int) (Math.random() * movies.size());
            movieTV.setText(movies.get(randomId));
            movies.remove(randomId);
        }
        else movieTV.setText("Run out of movies");
    }
}