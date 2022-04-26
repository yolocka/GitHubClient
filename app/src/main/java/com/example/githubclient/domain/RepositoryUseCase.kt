package com.example.githubclient.domain

import com.example.githubclient.data.web.entities.RepoDto
import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.data.web.entities.UserDto
import com.example.githubclient.domain.entities.UserEntity
import io.reactivex.rxjava3.core.Single

interface RepositoryUseCase {
    fun getUsers(callback: (List<UserEntity>) -> Unit)
    fun getOneUser(id: Long, callback: (UserEntity) -> Unit)
    fun addUser(user: UserEntity, callback: (Boolean) -> Unit)
    fun getRepositories(id: Long, callback: (List<RepoEntity>) -> Unit)
    fun addRepo(repository: RepoEntity, callback: (Boolean) -> Unit)
    fun observeUsersRepos(login: String) : Single<List<RepoDto>>
    fun getUsersFromRemoteSource() : Single<List<UserDto>>
}