package com.brandon.corejava.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ForzenEntityJava.class}, version = 1)
public abstract class ForzenDatabaseJava extends RoomDatabase {
    public abstract ForzenDaoJava forzenDaoJava();

    public static final String databaseName = "Forzen_DB";
}
