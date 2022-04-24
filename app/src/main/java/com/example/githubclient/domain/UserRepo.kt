package com.example.githubclient.domain

import com.example.githubclient.domain.entities.RepoDTO
import com.example.githubclient.domain.entities.UserDTO

interface UserRepo {

    fun getAllUsers(): List<UserDTO>

    fun getUser(id: Int): UserDTO

    fun saveUser(user: UserDTO) : Boolean

/*    fun updateUserData(id: Int, user: UserProfile)*/

    fun deleteUser(id: Int)

    fun deleteAllUsers()

    fun getRepositoriesList(id: Int): List<RepoDTO>

    fun addRepo(repo: RepoDTO) : Boolean
}