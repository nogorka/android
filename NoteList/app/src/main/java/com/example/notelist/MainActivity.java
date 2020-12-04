package com.example.notelist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText note;
    EditText date;
    TextView total;
    ListView notelist;

    DBHelper helper;
    SQLiteDatabase noteDB;

    SimpleCursorAdapter adapter; // объявлен в классе, чтобы был доступен вл всех методах
    Cursor notes;

    String[] notes_fields;
    int[] views = {R.id.id, R.id.note, R.id.date};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notelist = findViewById(R.id.notelist);
        date = findViewById(R.id.date_ed);
        note = findViewById(R.id.note_ed);

        total = findViewById(R.id.total);


        helper = new DBHelper(this);
        noteDB = helper.getWritableDatabase();

        notes = noteDB.rawQuery("SELECT * FROM notes", null);
        notes_fields = notes.getColumnNames();

        adapter = new SimpleCursorAdapter(this, R.layout.notes_item, notes, notes_fields, views, 0);
        notelist.setAdapter(adapter);

        total.setText("Total\t\t" + Integer.valueOf(notelist.getAdapter().getCount()));
    }

    public void onClick(View v) {

        ContentValues cv = new ContentValues();
        cv.put("note", note.getText().toString());
        cv.put("date", date.getText().toString());
        noteDB.insert("notes", null, cv);

        notes = noteDB.rawQuery("SELECT * FROM notes", null);
        adapter = new SimpleCursorAdapter(this, R.layout.notes_item, notes, notes_fields, views, 0);
        notelist.setAdapter(adapter);

        total.setText("Total\t\t" + Integer.valueOf(notelist.getAdapter().getCount()));
        date.setText("");
        note.setText("");
    }
}
