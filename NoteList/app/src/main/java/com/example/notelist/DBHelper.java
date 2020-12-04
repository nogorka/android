package com.example.notelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 10;
    final static String DB_NAME = "note.db";
    final static String TABLE_NAME = "notes";

    final static String CREATE =
            "CREATE TABLE " + TABLE_NAME + "( " +
                    "`_id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`note` TEXT NOT NULL, " +
                    "`date` VARCHAR NOT NULL " +
                    ")";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // выполняется, если версия базы данных отличается
        db.execSQL("DROP DATABASE " + DB_NAME);
        this.onCreate(db);
    }
}