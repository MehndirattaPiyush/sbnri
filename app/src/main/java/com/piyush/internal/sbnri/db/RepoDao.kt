package com.piyush.internal.sbnri.db

import androidx.paging.DataSource
import androidx.room.*

@Dao
interface RepoDao {
    @Query("SELECT * FROM RepoEntity")
    fun getAll(): DataSource.Factory<Int, RepoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)
}