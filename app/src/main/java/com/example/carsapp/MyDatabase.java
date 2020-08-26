package com.example.carsapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "car_db";
    public static final int DB_VERSION = 1;

    public static final String CAR_TB_NAME = "car";
    public static final String CAR_CLN_ID = "id";
    public static final String CAR_CLN_MODEL = "model";
    public static final String CAR_CLN_COLOR = "color";
    public static final String CAR_CLN_DPL = "distancePerLeter";

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        create new table
        db.execSQL("CREATE TABLE " + CAR_TB_NAME + " (" + CAR_CLN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + CAR_CLN_MODEL + " TEXT," + CAR_CLN_COLOR + " TEXT," + CAR_CLN_DPL + " REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS car");
        onCreate(db);
    }

    public boolean insertCar(Car car) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CAR_CLN_MODEL, car.getModel());
        values.put(CAR_CLN_COLOR, car.getColor());
        values.put(CAR_CLN_DPL, car.getDpl());

        long result = database.insert(CAR_TB_NAME, null, values);
        return result != -1;
    }

    public boolean updateCar(Car car) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CAR_CLN_MODEL, car.getModel());
        values.put(CAR_CLN_COLOR, car.getColor());
        values.put(CAR_CLN_DPL, car.getDpl());

        String arg[] = {String.valueOf(car.getId())};
        long result = database.update(CAR_TB_NAME, values, "id=?", arg);
        return result > 0;
    }

    public long getCarsCount() {
        SQLiteDatabase database = getReadableDatabase();
        return DatabaseUtils.queryNumEntries(database, CAR_TB_NAME);
    }

    public boolean deletCar(Car car) {
        SQLiteDatabase database = getWritableDatabase();
        String arg[] = {String.valueOf(car.getId())};
        int result = database.delete(CAR_TB_NAME, "id=?", arg);
        return result > 0;
    }

}
