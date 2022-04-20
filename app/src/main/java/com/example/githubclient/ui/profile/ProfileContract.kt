package com.example.githubclient.ui.profile

import com.example.githubclient.domain.entities.RepoDTO


class ProfileContract {
    interface ViewModel {
        fun getUserData(id: Long)
        fun getRepoList(id: Long)
        fun observeUsersRepo(login: String)
        fun addRepoToLocalRepo(repo: RepoDTO)
    }
}