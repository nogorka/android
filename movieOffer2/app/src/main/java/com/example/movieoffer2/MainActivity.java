package com.example.movieoffer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.movieoffer2.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

class Movie {
    String Title;
    String Year;
    String Rated;
    String Released;
    String Runtime;
    String Genre;
}

class Movies {
    ArrayList<Movie> movies;
}

public class MainActivity extends AppCompatActivity {

    TextView title;
    TextView released;
    TextView runtime;
    TextView genre;
    TextView year;
    TextView rated;

    ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = loadMovies();
        genre = findViewById(R.id.genreTV);
        title = findViewById(R.id.titleTV);
        year = findViewById(R.id.yearTV);
        rated = findViewById(R.id.ratedTV);
        runtime = findViewById(R.id.runtimeTV);
        released = findViewById(R.id.releasedTV);

    }

    public ArrayList<Movie> loadMovies() {
        InputStream stream = null;
        try {
            stream = getAssets().open("movies.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStreamReader reader = new InputStreamReader(stream);
        Gson gson = new Gson();
        Movies ms = gson.fromJson(reader, Movies.class);
        return ms.movies;
    }

    public void onClick(View v) {

        if (movies.size() != 0) {
            int randomId = (int) (Math.random() * movies.size());

            genre.setText(movies.get(randomId).Genre);
            title.setText(movies.get(randomId).Title);
            rated.setText(movies.get(randomId).Rated);
            released.setText(movies.get(randomId).Released);
            year.setText(movies.get(randomId).Year);
            runtime.setText(movies.get(randomId).Runtime);

            movies.remove(randomId);
        } else {
            genre.setText("Run out of movies");
            title.setText("");
            rated.setText("");
            released.setText("");
            year.setText("");
            runtime.setText("");
        }
    }
}