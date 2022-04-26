package com.example.githubclient

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.githubclient.data.db.UserRepoImpl
import com.example.githubclient.data.db.UsersDAO
import com.example.githubclient.data.db.UsersDataBase
import com.example.githubclient.data.UsersUseCaseImpl
import com.example.githubclient.data.web.UserRemoteRepoImpl
import com.example.githubclient.domain.UserRemoteRepo
import com.example.githubclient.domain.UserRepo
import com.example.githubclient.domain.UsersUseCase
import com.example.githubclient.utils.ViewModelStore
import java.lang.Exception

class App : Application() {

    private val remoteRepo: UserRemoteRepo by lazy {
        UserRemoteRepoImpl()
    }

    private val usersRepo: UserRepo by lazy {
        UserRepoImpl(UsersDataBase.getInstance(this))
    }

    val usersUseCase: UsersUseCase by lazy {
        UsersUseCaseImpl(app.usersRepo, Handler(Looper.getMainLooper()), remoteRepo)
    }

    val viewModelStore by lazy { ViewModelStore() }
}

val Context.app: App
    get() = applicationContext as App


val Fragment.app: App
    get() = requireActivity().app