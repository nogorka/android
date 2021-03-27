package com.example.picassolist;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.products_list);

        this.openHelper = new DBHelper(getApplicationContext());
        db = openHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from table1", null);

        ProductsAdapter adapter = new ProductsAdapter(this, cursor, 0);
        listView.setAdapter(adapter);
        db.close();
    }
}