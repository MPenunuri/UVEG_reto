package com.retouveg;
import android.provider.BaseColumns;
public class RestaurantContract {
    private RestaurantContract() {};
    public static class RestaurantEntry implements  BaseColumns {
        public static final String TABLE_NAME = "restaurant";
        public static final String COLUMN_NAME = "name";
        public static final String SQL_CREATE_RESTAURANT_ENTRY =
                "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " (" +
                        RestaurantEntry._ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_NAME + " TEXT)";
    }
    public static class FoodEntry implements  BaseColumns {
        public static final String TABLE_NAME = "food";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
        public static  final String SQL_CREATE_FOOD_ENTRY =
                "CREATE TABLE " + FoodEntry.TABLE_NAME + " (" +
                        FoodEntry._ID  + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, " +
                        COLUMN_PRICE + " DOUBLE, " + COLUMN_DESCRIPTION + " TEXT, " +
                        COLUMN_TYPE + " TEXT, " + COLUMN_RESTAURANT_ID + " INTEGER, " +
                        " FOREIGN KEY(" +  COLUMN_RESTAURANT_ID + ") REFERENCES " +
                        RestaurantEntry.TABLE_NAME + " (" + RestaurantEntry._ID + "))";
    }
}
