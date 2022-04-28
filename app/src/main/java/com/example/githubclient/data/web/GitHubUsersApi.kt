package com.example.githubclient.data.web

import com.example.githubclient.data.web.entities.UserDto
import com.example.githubclient.domain.GitHubApi
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface GitHubUsersApi : GitHubApi {
    @GET("users")
    fun listUsers() : Single<List<UserDto>>
}