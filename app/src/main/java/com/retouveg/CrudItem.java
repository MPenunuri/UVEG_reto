package com.retouveg;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

public class CrudItem {
    public long insert(String tableName, ContentValues values) {
        DBHelper dbHelper = App.getDBHelper();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(tableName, null, values);
        db.close();
        return rowId;
    }

    public int update(String tableName, ContentValues values,
                      String selection, String[] selectionArgs) {
        DBHelper dbHelper = App.getDBHelper();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.update(tableName, values, selection, selectionArgs);
        db.close();
        return rows;
    }
    public int delete(String tableName, String selection, String[] selectionArgs) {
        DBHelper dbHelper = App.getDBHelper();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rows = db.delete(tableName, selection, selectionArgs);
        db.close();
        return rows;
    }

    public Cursor query(String tableName, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        DBHelper dbHelper = App.getDBHelper();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.query(tableName, projection, selection, selectionArgs,
                null, null, sortOrder);
    }
}
