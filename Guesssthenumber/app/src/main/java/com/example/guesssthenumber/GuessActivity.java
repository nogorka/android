package com.example.guesssthenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GuessActivity extends AppCompatActivity {

    String QUERY_LESS = "Is number less than ";
    String QUERY_EQUAL = "Is number equal ";
    String QUESTION_MARK = "?";

    TextView guess;

    int start;
    int end;
    boolean ifSearch;
    boolean checkStep; // true if checked equals

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        start = getIntent().getIntExtra("first", 0);
        end = getIntent().getIntExtra("second", 100);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);
        guess = findViewById(R.id.guessTV);
        setEqual();
    }

    public int generateNewNumber(int first, int second) {
        return Math.round((second - first) / 2) + first;
    }

    public void setLess() {
        checkStep = false;
        ifSearch = true;
        guess.setText(QUERY_LESS + String.valueOf(generateNewNumber(start, end)) + QUESTION_MARK);
    }

    public void setEqual() {
        checkStep = true;
        ifSearch = false;
        guess.setText(QUERY_EQUAL + generateNewNumber(start, end) + QUESTION_MARK);
    }

    public void onClick(View v) {
        int current = generateNewNumber(start, end);
        switch (v.getId()) {
            case R.id.yes:
                if (ifSearch) {

                    if (!checkStep) end = current;

                    if (end - start > 1) {

                        if (checkStep) setLess();
                        else setEqual();
                    } else setEqual();

                } else
                    guess.setText("Hurray, we've find the number");
                break;


            case R.id.no:
                if (ifSearch) {

                    if (!checkStep) start = current;

                    if (end - start > 1) {

                        if (checkStep) setLess();
                        else setEqual();
                    } else setEqual();

                } else if (end - start > 1)
                    ifSearch = true;
                else
                    guess.setText("I guess I'm confused");

                break;
        }
    }
}