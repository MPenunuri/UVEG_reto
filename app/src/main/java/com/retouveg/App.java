package com.retouveg;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.File;

public class App extends Application {
    private static DBHelper dbHelper;
    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = new DBHelper(this);
        if (databaseExists(this)) {
            deleteDatabase(this);
        }
        DataLoader.loadData(this);
    }
    public static DBHelper getDBHelper() {
        return dbHelper;
    }
    private boolean databaseExists(Context context) {
        File dbFile = context.getDatabasePath(DBHelper.DB_NAME);
        return dbFile.exists();
    }
    private void deleteDatabase(Context context) {
        context.deleteDatabase(DBHelper.DB_NAME);
    }
}