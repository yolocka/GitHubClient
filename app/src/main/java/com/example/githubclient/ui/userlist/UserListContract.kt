package com.example.githubclient.ui.userlist

import com.example.githubclient.domain.entities.RepoDTO
import com.example.githubclient.domain.entities.UserDTO

class UserListContract {

    interface ViewModel {
        fun getUsers()
        fun updateData(userProfile: UserDTO)
        fun getUsersFromRemoteSource(isItFirstTime: Boolean)
    }
}