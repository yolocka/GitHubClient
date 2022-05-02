package com.example.githubclient.data

import android.os.Handler
import com.example.githubclient.data.web.entities.RepoDto
import com.example.githubclient.domain.RemoteRepository
import com.example.githubclient.domain.LocalRepository
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.data.web.entities.UserDto
import com.example.githubclient.domain.entities.UserEntity
import io.reactivex.rxjava3.core.Single

class RepositoryUseCaseImpl(
    private val repo: LocalRepository,
    private val uiHandler: Handler,
    private val remoteRepository: RemoteRepository
) : RepositoryUseCase {

    override fun getUsers() : Single<List<UserEntity>> {
        return repo.getAllUsers()
    }

    override fun getOneUser(id: Long, callback: (UserEntity) -> Unit) {
        Thread {
            val result = repo.getUser(id)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun addUser(user: UserEntity, callback: (Boolean) -> Unit) {
        Thread {
            val result = repo.saveUser(user)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun getRepositories(id: Long) : Single<List<RepoEntity>> {
        return repo.getRepositoriesList(id)
    }

    override fun addRepo(repository: RepoEntity, callback: (Boolean) -> Unit) {
        Thread {
            val result = repo.addRepo(repository)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun observeUsersRepos(login: String): Single<List<RepoDto>> {
        return remoteRepository.observeUsersRepos(login)
    }

    override fun getUsersFromRemoteSource(): Single<List<UserDto>> {
        return remoteRepository.observeUsersList()
    }
}