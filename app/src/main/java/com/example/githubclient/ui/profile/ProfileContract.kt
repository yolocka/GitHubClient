package com.example.githubclient.ui.profile


class ProfileContract {
    interface ViewModel {
        fun getUserData(id: Int)
        fun getRepoList(id: Int)
    }
}