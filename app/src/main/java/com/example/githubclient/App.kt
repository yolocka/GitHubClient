package com.example.githubclient

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.example.githubclient.data.db.LocalRepositoryImpl
import com.example.githubclient.data.db.UsersDataBase
import com.example.githubclient.data.RepositoryUseCaseImpl
import com.example.githubclient.data.web.RemoteRepositoryImpl
import com.example.githubclient.domain.RemoteRepository
import com.example.githubclient.domain.LocalRepository
import com.example.githubclient.domain.RepositoryUseCase
import com.example.githubclient.utils.ViewModelStore

class App : Application() {

    private val remoteRepository: RemoteRepository by lazy {
        RemoteRepositoryImpl()
    }

    private val usersRepo: LocalRepository by lazy {
        LocalRepositoryImpl(UsersDataBase.getInstance(this))
    }

    val repositoryUseCase: RepositoryUseCase by lazy {
        RepositoryUseCaseImpl(app.usersRepo, Handler(Looper.getMainLooper()), remoteRepository)
    }

    val viewModelStore by lazy { ViewModelStore() }
}

val Context.app: App
    get() = applicationContext as App


val Fragment.app: App
    get() = requireActivity().app