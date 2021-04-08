package com.example.roomcatalog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    CatalogDB db;
    ListView listView;
    Context cntx;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);

        cntx = getApplicationContext();
        db = CatalogDB.getInstance(cntx);


        GetProducts gp = new GetProducts();
        gp.execute();
    }


    class GetProducts extends AsyncTask<Void, Void, List<Product>> {

        protected List<Product> doInBackground() {
            return db.dao().getAllProducts();
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            List<Product> products = db.dao().getAllProducts();
            Log.d("tag", "prods1 "+products);
            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            Log.d("tag", "prods "+products);
            //create adapter
            List<Map<String, String>> myMapList = new ArrayList<Map<String, String>>();
            for (Product product : products) {
                Map<String, String> myMap = new HashMap<String, String>();
                myMap.put("id", Integer.toString(product.id));
                myMap.put("category_id", Integer.toString(product.category_id));
                myMap.put("name", product.name);
                myMapList.add(myMap);
            }
            ProductAdapter adapter = new ProductAdapter(cntx, myMapList, R.layout.item,
                    new String[]{"id", "name", "c_id"},
                    new int[]{R.id.id, R.id.name, R.id.category_id});
            listView.setAdapter(adapter);
        }


    }
}
