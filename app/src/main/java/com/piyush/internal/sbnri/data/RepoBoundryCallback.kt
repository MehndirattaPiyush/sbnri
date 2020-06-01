package com.piyush.internal.sbnri.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.piyush.internal.sbnri.Api
import com.piyush.internal.sbnri.db.RepoDao
import com.piyush.internal.sbnri.db.RepoEntity
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

class RepoBoundaryCallback @Inject constructor(
    private val api: Api,
    private val repoDao: RepoDao,
    private val activityScope: CoroutineScope
) :
    PagedList.BoundaryCallback<RepoEntity>() {

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()

    // LiveData of network errors.
    val networkErrors: LiveData<String>
        get() = _networkErrors

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() {
        getRemoteData()
    }

    override fun onItemAtEndLoaded(itemAtEnd: RepoEntity) {
        getRemoteData()
    }

    private fun getRemoteData() {
        if (isRequestInProgress) return
        GlobalScope.launch(CoroutineExceptionHandler { _, throwable ->
            _networkErrors.postValue(throwable.message)
            markProgress(false)
        }) {
            markProgress(true)
            val response = api.getRepos(
                lastRequestedPage,
                NETWORK_PAGE_SIZE
            )
            if (response.isSuccessful) {
                val list = response.body()?.map {
                    RepoEntity(it)
                }
                list?.let {
                    repoDao.insertAll(it)
                    markProgress(false)
                    lastRequestedPage++
                }

            } else {
                _networkErrors.postValue(response.message())
                markProgress(false)
            }
        }
    }

    private fun markProgress(isLoading: Boolean) {
        isRequestInProgress = isLoading
        _isLoading.postValue(isLoading)
    }
}