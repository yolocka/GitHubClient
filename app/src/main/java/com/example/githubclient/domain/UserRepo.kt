package com.example.githubclient.domain

import com.example.githubclient.data.entities.RepoDto
import com.example.githubclient.data.entities.UserDto

interface UserRepo {

   // fun getAllUsers(): List<OwnerDTO>

    fun getAllUsers(): List<UserDto>

    fun getUser(id: Long): UserDto

    fun saveUser(user: UserDto) : Boolean

/*    fun updateUserData(id: Int, user: UserProfile)*/

    fun deleteUser(id: Int)

    fun deleteAllUsers()

    fun getRepositoriesList(id: Long): List<RepoDto>

    fun addRepo(repo: RepoDto) : Boolean
}