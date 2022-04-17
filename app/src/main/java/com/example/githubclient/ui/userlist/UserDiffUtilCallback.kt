package com.example.githubclient.ui.userlist

import androidx.recyclerview.widget.DiffUtil
import com.example.githubclient.domain.entities.UserDTO

class UserDiffUtilCallback(private val oldList: List<UserDTO>, private val newList: List<UserDTO>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser: UserDTO = oldList[oldItemPosition]
        val newUser: UserDTO = newList[newItemPosition]
        return oldUser == newUser
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser: UserDTO = oldList[oldItemPosition]
        val newUser: UserDTO = newList[newItemPosition]
        return (oldUser.name == newUser.name
                && oldUser.photo == newUser.photo)
    }
}