package com.example.githubclient.domain

import com.example.githubclient.data.web.entities.RepoDto
import com.example.githubclient.data.web.entities.UserDto
import io.reactivex.rxjava3.core.Single

interface UserRemoteRepo {
    fun observeUsersRepos(login: String): Single<List<RepoDto>>
    fun observeUsersList(): Single<List<UserDto>>
}