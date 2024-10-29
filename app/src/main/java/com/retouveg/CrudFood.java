package com.retouveg;

import android.content.ContentValues;
import android.database.Cursor;

public class CrudFood extends CrudItem {
    private final String tableName = RestaurantContract.FoodEntry.TABLE_NAME;

    private ContentValues getFoodValues(Food food, long restaurantId) {
        ContentValues values = new ContentValues();
        values.put(RestaurantContract.FoodEntry.COLUMN_NAME, food.name);
        values.put(RestaurantContract.FoodEntry.COLUMN_PRICE, food.price.doubleValue());
        values.put(RestaurantContract.FoodEntry.COLUMN_DESCRIPTION, food.description);
        values.put(RestaurantContract.FoodEntry.COLUMN_TYPE, food.type);
        values.put(RestaurantContract.FoodEntry.COLUMN_RESTAURANT_ID, restaurantId);
        return values;
    }
    public long insertFood(Food food, long restaurantId) {
        ContentValues values = getFoodValues(food, restaurantId);
        return insert(tableName, values);
    }

    public int updateFood(Food food, long restaurantId,
                          String selection, String[] selectionArgs) {
        ContentValues values = getFoodValues(food, restaurantId);
        return update(tableName, values, selection, selectionArgs);
    }
    public int deleteFood(String selection, String[] selectionArgs) {
        return delete(tableName, selection, selectionArgs);
    }

    public Cursor queryFood(String selection, String[] selectionArgs) {
        return query(tableName, null, selection, selectionArgs, null);
    }
}
