package com.example.translateapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()) // ответ от сервера в виде строки
            .baseUrl(AzureTranslationAPI.API_URL) // адрес API сервера
            .build();

    AzureTranslationAPI api = retrofit.create(AzureTranslationAPI.class); // описываем, какие функции реализованы

    Spinner spinner;
    ArrayAdapter adapter;
    ArrayList<String> languages;
    String currentLanguage;

    EditText input;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Call<LanguagesResponse> call = api.getLanguages(); // создаём объект-вызов
        call.enqueue(new LanguagesCallback());

        spinner = findViewById(R.id.spinner);

        input = findViewById(R.id.input);
        output = findViewById(R.id.output);
    }

    public void setSpinner() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                Log.d("mytag", "\nitem " + item);
                String[] curLang = item.split(":");
                currentLanguage = curLang[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);
    }

    class Text {
        String Text;
    }

    public void onClick(View v) {
        String text = String.valueOf(input.getText());

        Log.d("mytag", "lang\t" + currentLanguage + "text\t" + text);

        Text msg = new Text();
        msg.Text = text;

        Text[] arr = {msg};

        Call<TranslatedText[]> call = api.translate(currentLanguage, arr); // создаём объект-вызов
        call.enqueue(new TranslateCallback());
    }

    public void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    // TODO: создать аналогичным образом класс для ответа сервера при переводе текста
    class LanguagesCallback implements Callback<LanguagesResponse> {

        @Override
        public void onResponse(Call<LanguagesResponse> call, Response<LanguagesResponse> response) {
            if (response.isSuccessful()) {

                Log.d("mytag", "response: " + response.body());

                languages = response.body().getLanguages();
                setSpinner();

            } else {

                Log.d("mytag", "error" + response.code());
                showToast("error" + response.code());
            }
        }

        @Override
        public void onFailure(Call<LanguagesResponse> call, Throwable t) {

            Log.d("mytag", "error: " + t.getLocalizedMessage());
            showToast(t.getLocalizedMessage());
        }
    }

    class TranslateCallback implements Callback<TranslatedText[]> {

        @Override
        public void onResponse(Call<TranslatedText[]> call, Response<TranslatedText[]> response) {
            if (response.isSuccessful()) {
                Log.d("mytag", "text response: " + response.body()[0].toString());
                output.setText(response.body()[0].getTranslations());
            } else {
                try {
                    Log.d("mytag", "error" + response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                showToast("text error" + response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<TranslatedText[]> call, Throwable t) {

            Log.d("mytag", "text fall: " + t.getLocalizedMessage());
            showToast(t.getLocalizedMessage());
        }
    }
}
