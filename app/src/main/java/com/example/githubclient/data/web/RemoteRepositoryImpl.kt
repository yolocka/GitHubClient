package com.example.githubclient.data.web

import com.example.githubclient.data.web.entities.RepoDto
import com.example.githubclient.data.web.entities.UserDto
import com.example.githubclient.domain.GitHubApi
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.domain.entities.UserEntity
import io.reactivex.rxjava3.core.Single

class RemoteRepositoryImpl(private val api: GitHubApi) : RepositoryUseCase{
    override fun getUsers(): Single<List<UserEntity>> {
        TODO("Not yet implemented")
    }

    override fun getOneUser(id: Long, callback: (UserEntity) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun addUser(user: UserEntity, callback: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getRepositories(id: Long): Single<List<RepoEntity>> {
        TODO("Not yet implemented")
    }

    override fun addRepo(repository: RepoEntity, callback: (Boolean) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun observeUsersRepos(login: String): Single<List<RepoDto>> {
        return (api as GitHubReposApi).listRepos(login)
    }

    override fun getUsersFromRemoteSource(): Single<List<UserDto>> {
        return (api as GitHubUsersApi).listUsers()
    }
}