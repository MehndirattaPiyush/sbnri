package com.piyush.internal.sbnri

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.google.gson.annotations.SerializedName
import com.piyush.internal.sbnri.db.RepoEntity
import java.lang.StringBuilder

/*
*   {
    "id": 417862,
    "name": "octokit.rb",
    "description": "Ruby toolkit for the GitHub API",
    "open_issues_count": 50,
    "license": {
      "key": "mit",
      "name": "MIT License",
      "spdx_id": "MIT",
      "url": "https://api.github.com/licenses/mit",
      "node_id": "MDc6TGljZW5zZTEz"
    },
    "permissions": {
      "admin": false,
      "push": false,
      "pull": true
    }
  }
* */

data class RepositoryModel(
    val id: Int?,
    @SerializedName("open_issues_count") val openIssuesCount: Int?,
    val license: Lisense?,
    val permissions: Permissions?,
    val name: String?,
    val description: String?
)

data class Lisense(
    val key: String?,
    val name: String?,
    @SerializedName("spdx_id") val spdxId: String?,
    val url: String?,
    @SerializedName("node_id") val nodeId: String?
){
    override fun toString(): String {
        return name?: ""
    }
}

data class Permissions(
    val admin: Boolean?,
    val push: Boolean?,
    val pull: Boolean?
){
    override fun toString(): String {
        val sb = StringBuilder()
        if(admin == true){
            sb.append("Admin ")
        }
        if(push == true){
            sb.append(" Push ")
        }
        if(pull == true){
            sb.append(" Pull")
        }
        return sb.toString()
    }
}

data class RepoResult(
    val data: LiveData<PagedList<RepoEntity>>,
    val networkErrors: LiveData<String>,
    val isLoading: LiveData<Boolean>
)