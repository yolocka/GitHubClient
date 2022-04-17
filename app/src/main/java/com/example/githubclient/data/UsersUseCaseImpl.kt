package com.example.githubclient.data

import android.os.Handler
import com.example.githubclient.domain.UserRepo
import com.example.githubclient.domain.UsersUseCase
import com.example.githubclient.domain.entities.UserProfile

class UsersUseCaseImpl(
    private val repo: UserRepo,
    private val uiHandler: Handler
) : UsersUseCase {

    override fun getUsers(callback: (List<UserProfile>) -> Unit) {
        Thread {
            val result = repo.getAllUsers()
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun getOneUser(id: Int, callback: (UserProfile) -> Unit) {
        Thread {
            val result = repo.getUser(id)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

    override fun addUser(user: UserProfile, callback: (Boolean) -> Unit) {
        Thread {
            val result = repo.saveUser(user)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }
}