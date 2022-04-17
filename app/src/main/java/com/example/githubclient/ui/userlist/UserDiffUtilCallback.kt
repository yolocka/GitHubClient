package com.example.githubclient.ui.userlist

import androidx.recyclerview.widget.DiffUtil
import com.example.githubclient.domain.entities.UserProfile

class UserDiffUtilCallback(private val oldList: List<UserProfile>, private val newList: List<UserProfile>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser: UserProfile = oldList[oldItemPosition]
        val newUser: UserProfile = newList[newItemPosition]
        return oldUser == newUser
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser: UserProfile = oldList[oldItemPosition]
        val newUser: UserProfile = newList[newItemPosition]
        return (oldUser.name == newUser.name
                && oldUser.photo == newUser.photo)
    }
}