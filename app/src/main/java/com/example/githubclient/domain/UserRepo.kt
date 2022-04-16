package com.example.githubclient.domain

import com.example.githubclient.domain.entities.UserProfile

interface UserRepo {

    fun getAllUsers(): List<UserProfile>

    fun getUser(id: Int): UserProfile

    fun saveUser(user: UserProfile) : Boolean

/*    fun updateUserData(id: Int, user: UserProfile)*/

    fun deleteUser(id: Int)

    fun deleteAll()
}