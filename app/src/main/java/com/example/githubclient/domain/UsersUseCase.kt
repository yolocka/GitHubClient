package com.example.githubclient.domain

import com.example.githubclient.domain.entities.RepoDTO
import com.example.githubclient.domain.entities.UserDTO

interface UsersUseCase {
    fun getUsers(callback: (List<UserDTO>) -> Unit)
    fun getOneUser(id: Int, callback: (UserDTO) -> Unit)
    fun addUser(user: UserDTO, callback: (Boolean) -> Unit)
    fun getRepositories(id: Int, callback: (List<RepoDTO>) -> Unit)
    fun addRepo(repository: RepoDTO, callback: (Boolean) -> Unit)
}