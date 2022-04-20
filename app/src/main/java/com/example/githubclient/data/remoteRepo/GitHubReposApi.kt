package com.example.githubclient.data.remoteRepo

import com.example.githubclient.domain.entities.RepoDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubReposApi {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?) : Single<List<RepoDTO>>
}