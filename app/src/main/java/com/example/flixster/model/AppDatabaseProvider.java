package com.example.flixster.model;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseProvider {

    private static AppDatabase db = null;

    private AppDatabaseProvider(){};

    private static void initializeDB(Context context) {
        AppDatabaseProvider.db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "db-name")
                .allowMainThreadQueries().build();
    }



    public static AppDatabase getInstance(Context context){
        if (db == null){
            initializeDB(context);
        }
        return db;
    }
}
