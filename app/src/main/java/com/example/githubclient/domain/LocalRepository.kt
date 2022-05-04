package com.example.githubclient.domain

import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.domain.entities.UserEntity

interface LocalRepository {

    fun getAllUsers(): List<UserEntity>

    fun getUser(id: Long): UserEntity

    fun saveUser(user: UserEntity) : Boolean

/*    fun updateUserData(id: Int, user: UserProfile)*/

    fun deleteUser(id: Int)

    fun deleteAllUsers()

    fun getRepositoriesList(id: Long): List<RepoEntity>

    fun addRepo(repo: RepoEntity) : Boolean
}