package com.example.githubclient.data.web

import com.example.githubclient.data.entities.RepoDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubReposApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?) : Single<List<RepoDto>>
}