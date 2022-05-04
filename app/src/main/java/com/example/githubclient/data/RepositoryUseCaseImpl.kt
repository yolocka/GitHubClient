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

    override fun getUsers(callback: (List<UserEntity>) -> Unit) {
        Thread {
            val result = repo.getAllUsers()
            uiHandler.post {
                callback(result)
            }
        }.start()
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

    override fun getRepositories(id: Long, callback: (List<RepoEntity>) -> Unit) {
        Thread {
            val result = repo.getRepositoriesList(id)
            uiHandler.post {
                callback(result)
            }
        }.start()
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