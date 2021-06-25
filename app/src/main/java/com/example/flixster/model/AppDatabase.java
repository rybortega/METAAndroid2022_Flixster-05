package com.example.flixster.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Movie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
