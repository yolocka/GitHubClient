package com.example.githubclient.ui.profile

import androidx.recyclerview.widget.DiffUtil
import com.example.githubclient.data.entities.RepoDto

class RepoDiffUtilCallback (private val oldList: List<RepoDto>, private val newList: List<RepoDto>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepoItem: RepoDto = oldList[oldItemPosition]
        val newRepoItem: RepoDto = newList[newItemPosition]
        return oldRepoItem == newRepoItem
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRepoItem: RepoDto = oldList[oldItemPosition]
        val newRepoItem: RepoDto = newList[newItemPosition]
        return (oldRepoItem.name == newRepoItem.name
                && oldRepoItem.id == newRepoItem.id)
    }
}