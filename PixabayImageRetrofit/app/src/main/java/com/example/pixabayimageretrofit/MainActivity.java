package com.example.pixabayimageretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    String API_URL = "https://pixabay.com/";

    String key = "16115131-f2ac4e59ef4204b7d06f11215";
    String image_type = "all";
    MainActivity activity = this;
//    Accepted values: "all", "photo", "illustration", "vector"
//    Default: "all"

    ImageView imageView;
    ListView listView;
    EditText keyWords;

    interface PixabayAPI {
        @GET("/api")
        Call<Response> search(
                @Query("q") String q,
                @Query("key") String key,
                @Query("image_type") String image_type
        );

        @GET()
        Call<ResponseBody> getImage(@Url String pictureUrl);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image);
        listView = findViewById(R.id.list);
        keyWords = findViewById(R.id.search_text);
    }

    public void OnChangeType(View v) {
        switch (v.getId()) {
            case R.id.btn_all: {
                image_type = "all";
                break;
            }
            case R.id.btn_photo: {
                image_type = "photo";
                break;
            }
            case R.id.btn_vector: {
                image_type = "vector";
                break;
            }
            case R.id.btn_illustration: {
                image_type = "illustration";
                break;
            }
        }
    }

    public void OnSearch(View v) {
        startSearch(keyWords.getText().toString());
    }

    public void startSearch(String text) {
        Log.d("mytag", "keywords " + text);

        //retrofit for search query
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PixabayAPI api = retrofit.create(PixabayAPI.class);

        //retrofit for download images
        Retrofit retrofitEmpty = new Retrofit.Builder().baseUrl(API_URL).build();
        final PixabayAPI apiEmpty = retrofitEmpty.create(PixabayAPI.class);

        Call<Response> call = api.search(text, key, image_type);

        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response r = response.body(); // получили ответ в виде объекта
                Log.d("mytag", "found hits:" + r.hits.length); // сколько картинок нашлось

                CustomAdapter adapter = new CustomAdapter(activity, r.hits, apiEmpty);
                listView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("mytag", "Error: " + t.getLocalizedMessage());
            }
        };
        call.enqueue(callback); // ставим запрос в очередь
    }


}