package com.example.pixabayimageretrofit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class CustomAdapter extends BaseAdapter {

    Context ctx;
    Hit[] hits;
    MainActivity.PixabayAPI api;

    public CustomAdapter(Context ctx, Hit[] hits, MainActivity.PixabayAPI api) {
        this.ctx = ctx;
        this.hits = hits;
        this.api = api;
    }

    @Override
    public int getCount() {
        return hits.length;
    }

    @Override
    public Object getItem(int i) {
        return hits[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(ctx).inflate(R.layout.item, viewGroup, false);
        ImageView imageView = view.findViewById(R.id.picture);
        TextView textView = view.findViewById(R.id.id);

        Hit hit = hits[i];

        Log.d("mytag", "id: " + hit.id + "\nimage_type:" + hit.type);
        textView.setText("id: " + hit.id + "\nimage_type:" + hit.type);

        imageView.setImageResource(R.drawable.no_image);

        downloadImage(api, hit, imageView);
        //imageView.setImageBitmap(image);

        Log.d("mytag", "Loading: " + hit.previewURL);

        return view;
    }

    public void downloadImage(MainActivity.PixabayAPI api, Hit hit, final ImageView imageView) {
        Log.d("mytag", "previewUrl:\n" + hit.previewURL);
        Call<ResponseBody> getImage = api.getImage(hit.previewURL);
        Callback<ResponseBody> imageCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                Log.d("mytag", "size: " + bmp.getByteCount());
                imageView.setImageBitmap(bmp);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("mytag", "fail:" + t.getLocalizedMessage());
            }
        };
        getImage.enqueue(imageCallback);
    }
}
