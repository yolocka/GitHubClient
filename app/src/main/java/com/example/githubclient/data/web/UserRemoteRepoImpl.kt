package com.example.githubclient.data.web

import com.example.githubclient.domain.UserRemoteRepo
import com.example.githubclient.data.entities.UserDto
import com.example.githubclient.data.entities.RepoDto
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class UserRemoteRepoImpl : UserRemoteRepo{
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val reposApi: GitHubReposApi = retrofit.create(GitHubReposApi::class.java)
    private val usersApi: GitHubUsersApi = retrofit.create(GitHubUsersApi::class.java)

    override fun observeUsersRepos(login: String): Single<List<RepoDto>> {
        return reposApi.listRepos(login)
    }

    override fun observeUsersList(): Single<List<UserDto>> {
        return usersApi.listUsers()
    }
}