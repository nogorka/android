package com.example.picassolist;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductsAdapter extends CursorAdapter {
    Picasso picasso;

    public ProductsAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
        picasso = new Picasso.Builder(context).build();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView picture = view.findViewById(R.id.picture);
        TextView textViewName = view.findViewById(R.id.name);
        TextView textViewPrice = view.findViewById(R.id.price);
        TextView textViewDesc = view.findViewById(R.id.description);

        int price = cursor.getInt(cursor.getColumnIndex("price"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String url = cursor.getString(cursor.getColumnIndex("url"));
        String description = cursor.getString(cursor.getColumnIndex("description"));

        textViewName.setText(name);
        textViewDesc.setText(description);
        textViewPrice.setText(Integer.toString(price));

        picasso.load(url).error(R.drawable.no_image).into(picture);
    }
}