package com.brandon.forzenbook.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ForzenEntity::class],
    version = 1,
)
abstract class ForzenDatabase: RoomDatabase() {
    abstract fun forzenDao(): ForzenDao

    companion object {
        const val NAME = "Forzen_DB"
    }
}