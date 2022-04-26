package com.example.githubclient.ui.profile

import androidx.recyclerview.widget.DiffUtil
import com.example.githubclient.domain.entities.RepoEntity

class RepoDiffUtilCallback (private val oldList: List<RepoEntity>, private val newList: List<RepoEntity>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepoItem: RepoEntity = oldList[oldItemPosition]
        val newRepoItem: RepoEntity = newList[newItemPosition]
        return oldRepoItem == newRepoItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepoItem: RepoEntity = oldList[oldItemPosition]
        val newRepoItem: RepoEntity = newList[newItemPosition]
        return (oldRepoItem.name == newRepoItem.name
                && oldRepoItem.id == newRepoItem.id)
    }
}