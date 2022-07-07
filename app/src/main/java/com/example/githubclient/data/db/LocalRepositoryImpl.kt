package com.example.githubclient.data.db

import android.os.Handler
import com.example.githubclient.data.db.entities.RepositoriesEntity
import com.example.githubclient.data.db.entities.UserProfileEntity
import com.example.githubclient.data.web.entities.RepoDto
import com.example.githubclient.data.web.entities.UserDto
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.domain.entities.RepoEntity
import com.example.githubclient.domain.entities.UserEntity
import io.reactivex.rxjava3.core.Single

class LocalRepositoryImpl(
    private val dao: UsersDAO,
    private val uiHandler: Handler
    ) : RepositoryUseCase {

    override fun getUsers(): Single<List<UserEntity>> {
        return dao.getAllUsers()
            .map {
                it.map { userEntity ->
                    UserEntity(
                        id = userEntity.gitHubId,
                        login = userEntity.userName,
                        avatar_url = userEntity.userPhoto
                    )
                }
            }
    }

    override fun getOneUser(id: Long, callback: (UserEntity) -> Unit) {
        Thread {
            val result = UserEntity (
                id = dao.getUser(id).gitHubId,
                login = dao.getUser(id).userName,
                avatar_url = dao.getUser(id).userPhoto
            )
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun addUser(user: UserEntity, callback: (Boolean) -> Unit) {
        Thread {
            dao.insertUser(
                UserProfileEntity(
                    id = 0,
                    gitHubId = user.id,
                    userName = user.login,
                    userPhoto = user.avatar_url
                )
            )
            val result = true
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun getRepositories(id: Long): Single<List<RepoEntity>> {
        return dao.getAllRepositories(id)
            .map {
                it.map { repoEntity ->
                    RepoEntity(
                        id = repoEntity.id,
                        name = repoEntity.name,
                        userId = repoEntity.userId
                    )
                }
            }
    }
    override fun addRepo(repository: RepoEntity, callback: (Boolean) -> Unit) {
        Thread {
            dao.insertRepo(
                RepositoriesEntity(
                    id = 0,
                    name = repository.name,
                    userId = repository.userId
                )
            )
            val result = true
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun observeUsersRepos(login: String): Single<List<RepoDto>> {
        TODO("Not yet implemented")
    }

    override fun getUsersFromRemoteSource(): Single<List<UserDto>> {
        TODO("Not yet implemented")
    }
}