package com.example.roomcatalog;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

public class CatalogClient {


//    private static final String DB_NAME = "catalog.db";
//
//    private Context cntx;
//    private static CatalogClient instance;
//    private CatalogDB db;
//
//    private CatalogClient(Context cntx) {
//        this.cntx = cntx;
//        db = Room.databaseBuilder(cntx, CatalogDB.class, "catalod").build();
//
//    }
//
//    public synchronized static CatalogDB getInstance(Context ctxt) {
//
//        if (instance == null) {
//            instance = create(ctxt, false);
//        }
//        return (instance);
//    }
//
//    static CatalogDB create(Context ctxt, boolean memoryOnly) {
//        RoomDatabase.Builder<CatalogDB> b;
//        if (memoryOnly) {
//            b = Room.inMemoryDatabaseBuilder(ctxt.getApplicationContext(), CatalogDB.class);
//        } else {
//            b = Room.databaseBuilder(ctxt.getApplicationContext(), CatalogDB.class, DB_NAME);
//        }
//        return (b.build());
//
//    }
//
//    public CatalogDB getCatalogDB() {
//        return db;
//    }
}