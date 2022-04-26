package com.example.githubclient.ui.userlist

import androidx.recyclerview.widget.DiffUtil
import com.example.githubclient.data.entities.UserEntity

class UserDiffUtilCallback(private val oldList: List<UserEntity>, private val newList: List<UserEntity>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser: UserEntity = oldList[oldItemPosition]
        val newUser: UserEntity = newList[newItemPosition]
        return oldUser == newUser
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser: UserEntity = oldList[oldItemPosition]
        val newUser: UserEntity = newList[newItemPosition]
        return (oldUser.login == newUser.login
                && oldUser.avatar_url == newUser.avatar_url)
    }
}