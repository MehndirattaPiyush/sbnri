package com.piyush.internal.sbnri.data

import androidx.paging.LivePagedListBuilder
import com.piyush.internal.sbnri.RepoResult
import com.piyush.internal.sbnri.db.RepoDao
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val repoDao: RepoDao,
    private val boundaryCallback: RepoBoundaryCallback
) {

    fun getRepos(): RepoResult {

        // Get data source factory from the local db
        val dataSourceFactory = repoDao.getAll()

        // live data to update network errors and loading state
        val networkErrors = boundaryCallback.networkErrors
        val isLoading = boundaryCallback.isLoading

        // Get the paged list
        val data = LivePagedListBuilder(dataSourceFactory,
            DATABASE_PAGE_SIZE
        )
            .setBoundaryCallback(boundaryCallback)
            .build()

        // Get the network errors exposed by the boundary callback
        return RepoResult(
            data,
            networkErrors,
            isLoading
        )
    }

    companion object {
        private const val DATABASE_PAGE_SIZE = 10

    }
}