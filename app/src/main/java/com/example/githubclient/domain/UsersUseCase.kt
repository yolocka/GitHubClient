package com.example.githubclient.domain

import com.example.githubclient.domain.entities.RepoDTO
import com.example.githubclient.domain.entities.UserDTO
import io.reactivex.rxjava3.core.Single

interface UsersUseCase {
    fun getUsers(callback: (List<UserDTO>) -> Unit)
    fun getOneUser(id: Long, callback: (UserDTO) -> Unit)
    fun addUser(user: UserDTO, callback: (Boolean) -> Unit)
    fun getRepositories(id: Long, callback: (List<RepoDTO>) -> Unit)
    fun addRepo(repository: RepoDTO, callback: (Boolean) -> Unit)
    fun observeUsersRepos(login: String) : Single<List<RepoDTO>>
    fun getUsersFromRemoteSource() : Single<List<UserDTO>>
}