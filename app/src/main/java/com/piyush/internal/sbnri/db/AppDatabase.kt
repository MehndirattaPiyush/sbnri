package com.piyush.internal.sbnri.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(RepoEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}