package com.example.roomcatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    CatalogDB db;
    ListView listView;
    Context context;

    List<Category> categoriesAll;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        context = getApplicationContext();
        db = CatalogDB.getInstance(context);


        GetCategory gc = new GetCategory();
        gc.execute();

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        GetProducts gp = new GetProducts();
                        gp.execute(i);
                    }
                }
        );
    }


    class GetProducts extends AsyncTask<Integer, Void, List<Product>> {

        @Override
        protected List<Product> doInBackground(Integer... index) {
            List<Product> products;
            if (index[0] == -1) {
                products = db.dao().getAllProducts();
            } else {
                products = db.dao().getAllProductByCategoryId(index[0] + 1);
            }
            return products;
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);

            //transform to adapter
            List<Map<String, String>> list = new ArrayList();
            for (Product p : products) {

                Map<String, String> lineMap = new HashMap();
                lineMap.put("id", Integer.toString(p.id));
                lineMap.put("name", p.name);

                //lineMap.put("category_id", Integer.toString(p.category_id));
                if (!categoriesAll.isEmpty() && categoriesAll != null)
                    for (Category c : categoriesAll) {
                        if (p.category_id == c.id) {
                            lineMap.put("category_id", c.name);
                        }
                    }
                list.add(lineMap);
            }

            //set adapter
            CatalogAdapter adapter = new CatalogAdapter(context, list, R.layout.product_item,
                    new String[]{"id", "name", "category_id"},
                    new int[]{R.id.id, R.id.name, R.id.category_id});
            listView.setAdapter(adapter);
        }
    }

    class GetCategory extends AsyncTask<Void, Void, List<Category>> {

        @Override
        protected List<Category> doInBackground(Void... voids) {
            List<Category> categories = db.dao().getAllCategories();
            categoriesAll = categories;
            return categories;
        }

        @Override
        protected void onPostExecute(List<Category> categories) {
            super.onPostExecute(categories);

            //transform to adapter
            List<Map<String, String>> list = new ArrayList();
            for (Category c : categories) {

                Map<String, String> lineMap = new HashMap();
                lineMap.put("id", Integer.toString(c.id));
                lineMap.put("name", c.name);
                list.add(lineMap);
            }

            //set adapter
            CatalogAdapter adapter = new CatalogAdapter(context, list, R.layout.category_item,
                    new String[]{"name"},
                    new int[]{R.id.name});
            listView.setAdapter(adapter);
        }
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back: {
                GetCategory gc = new GetCategory();
                gc.execute();
                break;
            }
            case R.id.all_products: {
                GetProducts gp = new GetProducts();
                gp.execute(-1);
                break;
            }
        }
    }

}
