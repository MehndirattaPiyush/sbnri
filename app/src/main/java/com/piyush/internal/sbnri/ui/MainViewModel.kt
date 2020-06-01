package com.piyush.internal.sbnri.ui

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.piyush.internal.sbnri.data.DataRepository
import com.piyush.internal.sbnri.db.RepoEntity
import com.piyush.internal.sbnri.RepoResult
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private val repoResult: LiveData<RepoResult> = run {
        val res = MutableLiveData<RepoResult>()
        viewModelScope.launch {
            res.value = dataRepository.getRepos()
        }
        res
    }

    val repos: LiveData<PagedList<RepoEntity>> = Transformations.switchMap(repoResult) { it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) {
        it.networkErrors
    }
    val isLoading: LiveData<Boolean> = Transformations.switchMap(repoResult) {
        it.isLoading
    }


}