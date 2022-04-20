package com.example.githubclient.data

import android.os.Handler
import com.example.githubclient.domain.UserRemoteRepo
import com.example.githubclient.domain.UserRepo
import com.example.githubclient.domain.UsersUseCase
import com.example.githubclient.domain.entities.RepoDTO
import com.example.githubclient.domain.entities.UserDTO
import io.reactivex.rxjava3.core.Single

class UsersUseCaseImpl(
    private val repo: UserRepo,
    private val uiHandler: Handler,
    private val remoteRepo: UserRemoteRepo
) : UsersUseCase {

    override fun getUsers(callback: (List<UserDTO>) -> Unit) {
        Thread {
            val result = repo.getAllUsers()
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun getOneUser(id: Long, callback: (UserDTO) -> Unit) {
        Thread {
            val result = repo.getUser(id)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun addUser(user: UserDTO, callback: (Boolean) -> Unit) {
        Thread {
            val result = repo.saveUser(user)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun getRepositories(id: Long, callback: (List<RepoDTO>) -> Unit) {
        Thread {
            val result = repo.getRepositoriesList(id)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun addRepo(repository: RepoDTO, callback: (Boolean) -> Unit) {
        Thread {
            val result = repo.addRepo(repository)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun observeUsersRepos(login: String): Single<List<RepoDTO>> {
        return remoteRepo.observeUsersRepos(login)
    }

    override fun getUsersFromRemoteSource(): Single<List<UserDTO>> {
        return remoteRepo.observeUsersList()
    }
}