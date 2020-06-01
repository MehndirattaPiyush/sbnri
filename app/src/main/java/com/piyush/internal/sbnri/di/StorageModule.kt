package com.piyush.internal.sbnri.di

import android.app.Application
import androidx.room.Room
import com.piyush.internal.sbnri.db.AppDatabase
import com.piyush.internal.sbnri.db.RepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule{
    @Singleton
    @Provides
    fun getDataBase(applicationContext: Application): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "repo-database"
        ).build()
    }

    @Singleton
    @Provides
    fun getRepoDao(appDatabase: AppDatabase): RepoDao {
        return appDatabase.repoDao()
    }

}