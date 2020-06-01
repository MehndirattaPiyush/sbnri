package com.piyush.internal.sbnri.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.ui.RepoViewHolder
import com.piyush.internal.sbnri.R
import com.piyush.internal.sbnri.db.RepoEntity
import com.piyush.internal.sbnri.databinding.RowListItemBinding
import javax.inject.Inject

class ReposAdapter @Inject constructor() :
    PagedListAdapter<RepoEntity, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<RowListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.row_list_item,
            parent,
            false
        )
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as RepoViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<RepoEntity>() {
            override fun areItemsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RepoEntity, newItem: RepoEntity): Boolean =
                oldItem == newItem
        }
    }
}