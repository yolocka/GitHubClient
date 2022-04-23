package com.example.githubclient.domain

import com.example.githubclient.data.entities.RepoDto
import com.example.githubclient.data.entities.UserDto
import io.reactivex.rxjava3.core.Single

interface UsersUseCase {
    fun getUsers(callback: (List<UserDto>) -> Unit)
    fun getOneUser(id: Long, callback: (UserDto) -> Unit)
    fun addUser(user: UserDto, callback: (Boolean) -> Unit)
    fun getRepositories(id: Long, callback: (List<RepoDto>) -> Unit)
    fun addRepo(repository: RepoDto, callback: (Boolean) -> Unit)
    fun observeUsersRepos(login: String) : Single<List<RepoDto>>
    fun getUsersFromRemoteSource() : Single<List<UserDto>>
}