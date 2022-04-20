package com.example.githubclient.domain

import com.example.githubclient.domain.entities.UserDTO
import com.example.githubclient.domain.entities.RepoDTO
import io.reactivex.rxjava3.core.Single

interface UserRemoteRepo {
    fun observeUsersRepos(login: String): Single<List<RepoDTO>>
    fun observeUsersList(): Single<List<UserDTO>>
}