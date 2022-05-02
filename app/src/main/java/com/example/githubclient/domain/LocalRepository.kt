package com.example.githubclient.domain

import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.domain.entities.UserEntity
import io.reactivex.rxjava3.core.Single

interface LocalRepository {

    fun getAllUsers(): Single<List<UserEntity>>

    fun getUser(id: Long): UserEntity

    fun saveUser(user: UserEntity) : Boolean

/*    fun updateUserData(id: Int, user: UserProfile)*/

    fun deleteUser(id: Int)

    fun deleteAllUsers()

    fun getRepositoriesList(id: Long): Single<List<RepoEntity>>

    fun addRepo(repo: RepoEntity) : Boolean
}