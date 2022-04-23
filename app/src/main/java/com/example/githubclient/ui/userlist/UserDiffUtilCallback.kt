package com.example.githubclient.ui.userlist

import androidx.recyclerview.widget.DiffUtil
import com.example.githubclient.data.entities.UserDto

class UserDiffUtilCallback(private val oldList: List<UserDto>, private val newList: List<UserDto>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser: UserDto = oldList[oldItemPosition]
        val newUser: UserDto = newList[newItemPosition]
        return oldUser == newUser
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser: UserDto = oldList[oldItemPosition]
        val newUser: UserDto = newList[newItemPosition]
        return (oldUser.login == newUser.login
                && oldUser.avatar_url == newUser.avatar_url)
    }
}