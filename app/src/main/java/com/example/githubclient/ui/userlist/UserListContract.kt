package com.example.githubclient.ui.userlist

import com.example.githubclient.data.entities.UserDto

class UserListContract {

    interface ViewModel {
        fun getUsers()
        fun updateData(userProfile: UserDto)
        fun getUsersFromRemoteSource(isItFirstTime: Boolean)
    }
}