package com.example.githubclient.domain

import com.example.githubclient.domain.entities.UserProfile

interface UsersUseCase {
    fun getUsers(callback: (List<UserProfile>) -> Unit)
    fun getOneUser(id: Int, callback: (UserProfile) -> Unit)
    fun addUser(user: UserProfile, callback: (Boolean) -> Unit)
}