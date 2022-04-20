package com.example.githubclient.data.remoteRepo

import com.example.githubclient.domain.entities.UserDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET

interface GitHubUsersApi {
    @GET("users")
    fun listUsers() : Single<List<UserDTO>>
}