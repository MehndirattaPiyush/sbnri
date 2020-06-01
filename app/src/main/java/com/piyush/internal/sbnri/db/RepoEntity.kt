package com.piyush.internal.sbnri.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.piyush.internal.sbnri.RepositoryModel

@Entity
data class RepoEntity constructor(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "open_issues_count") val openIssuesCount: Int?,
    @ColumnInfo(name = "permission") val permission: String?,
    @ColumnInfo(name = "license") val license: String?

){
    constructor(
        repositoryModel: RepositoryModel
    ) : this(
        id = repositoryModel.id,
        name = repositoryModel.name,
        description = repositoryModel.description,
        openIssuesCount = repositoryModel.openIssuesCount,
        permission = repositoryModel.permissions.toString(),
        license = repositoryModel.license.toString()
    )

}