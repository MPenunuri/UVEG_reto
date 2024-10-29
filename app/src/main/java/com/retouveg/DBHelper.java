package com.retouveg;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    public static final String DB_NAME = "restaurant";
    public  DBHelper (@NonNull Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("DBHelper", "constructor executed");
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RestaurantContract.RestaurantEntry.SQL_CREATE_RESTAURANT_ENTRY);
        db.execSQL(RestaurantContract.FoodEntry.SQL_CREATE_FOOD_ENTRY);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
