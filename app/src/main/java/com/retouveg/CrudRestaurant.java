package com.retouveg;

import android.content.ContentValues;
import android.database.Cursor;

public class CrudRestaurant extends CrudItem {
    private final String tableName = RestaurantContract.RestaurantEntry.TABLE_NAME;
    public long insertRestaurant(String name) {
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.RestaurantEntry.COLUMN_NAME, name);
        return insert(tableName, values);
    }
    public int updateRestaurant(String name, String selection, String[] selectionArgs) {
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.RestaurantEntry.COLUMN_NAME, name);
        return update(tableName, values, selection, selectionArgs);
    }
    public int deleteRestaurant(String selection, String[] selectionArgs) {
        return delete(tableName, selection, selectionArgs);
    }

    public Cursor queryAll() {
        return query(tableName, null,null, null, null);
    }
}
