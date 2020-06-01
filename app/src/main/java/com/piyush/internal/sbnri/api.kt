package com.piyush.internal.sbnri

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

public interface Api {

    @GET("octokit/repos")
    suspend fun getRepos(@Query("page") pageNumber: Int, @Query("per_page") perPage: Int): Response<List<RepositoryModel>>

}