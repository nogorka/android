package com.example.roomcatalog;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class ProductAdapter extends SimpleAdapter {

    public ProductAdapter(Context context, List<Map<String, String>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }
}
