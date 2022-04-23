package com.example.githubclient.data.web

import com.example.githubclient.data.entities.UserDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface GitHubUsersApi {
    @GET("users")
    fun listUsers() : Single<List<UserDto>>
}