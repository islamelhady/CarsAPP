package com.example.carsapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {
    public static final String DB_NAME = "car.db";
    public static final int DB_VERSION = 1;

    public static final String CAR_TB_NAME = "car";
    public static final String CAR_CLN_ID = "id";
    public static final String CAR_CLN_MODEL = "model";
    public static final String CAR_CLN_COLOR = "color";
    public static final String CAR_CLN_DESCRIPTION = "description";
    public static final String CAR_CLN_IMAGE = "image";
    public static final String CAR_CLN_DPL = "distancePerLeter";

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
////        create new table
//        db.execSQL("CREATE TABLE " + CAR_TB_NAME + " (" + CAR_CLN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                + CAR_CLN_MODEL + " TEXT," + CAR_CLN_COLOR + " TEXT," + CAR_CLN_DPL + " REAL)");
//
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS car");
        onCreate(db);
    }
}
