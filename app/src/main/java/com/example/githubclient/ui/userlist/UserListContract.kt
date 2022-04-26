package com.example.githubclient.ui.userlist

import com.example.githubclient.domain.entities.UserEntity

class UserListContract {

    interface ViewModel {
        fun getUsers()
        fun updateData(userProfile: UserEntity)
        fun getUsersFromRemoteSource(isItFirstTime: Boolean)
    }
}