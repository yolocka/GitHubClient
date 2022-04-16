package com.example.githubclient

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import com.example.githubclient.data.UserRepoImpl
import com.example.githubclient.data.UsersDAO
import com.example.githubclient.data.UsersDataBase
import com.example.githubclient.data.UsersUseCaseImpl
import com.example.githubclient.domain.UserRepo
import com.example.githubclient.domain.UsersUseCase
import java.lang.Exception

class App : Application() {

    val usersUseCase: UsersUseCase by lazy {
        UsersUseCaseImpl(app.usersRepo, Handler(Looper.getMainLooper()))
    }

    private val usersRepo: UserRepo by lazy {
        UserRepoImpl(getUsersDao())
    }

    companion object{
        private var appInstance: App? = null
        private var db: UsersDataBase? = null
        private const val DB_NAME = "Users.db"

        fun getUsersDao(): UsersDAO {
            if (db == null) {
                synchronized(UsersDataBase::class.java) {
                    if (db == null) {
                        appInstance?.let { app ->
                            db = Room.databaseBuilder(
                                app.applicationContext,
                                UsersDataBase::class.java,
                                DB_NAME
                            ).build()
                        } ?: throw Exception("")
                    }
                }
            }
            return db!!.usersDAO()
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }
}

val Context.app: App
    get() {
        return applicationContext as App
    }