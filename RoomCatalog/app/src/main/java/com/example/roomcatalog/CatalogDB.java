package com.example.roomcatalog;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Category.class}, version = 1)
public abstract class CatalogDB extends RoomDatabase {

    abstract CatalogDAO dao();

    private static final String DB_NAME = "catalog.db";
    private static CatalogDB instance = null;

    public synchronized static CatalogDB getInstance(Context ctxt) {

        if (instance == null) {
            instance = create(ctxt, false);
        }
        return (instance);
    }

    static CatalogDB create(Context ctxt, boolean memoryOnly) {
        RoomDatabase.Builder<CatalogDB> b = null;
        if (!memoryOnly) {
            b = Room
                    .databaseBuilder(ctxt.getApplicationContext(), CatalogDB.class, DB_NAME)
                    .createFromAsset("databases/catalog.db");
        }
        return (b.build());
    }

}
