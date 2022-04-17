package com.example.githubclient.ui.userlist

import com.example.githubclient.domain.entities.UserProfile

class UserListContract {

    interface ViewModel {
        fun getUsers()
        fun updateData(userProfile: UserProfile)
    }
}