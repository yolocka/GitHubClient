package com.example.githubclient.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubclient.data.entities.RepositoriesEntity
import com.example.githubclient.data.entities.UserProfileEntity

@Database(entities = [UserProfileEntity::class, RepositoriesEntity::class], version = 1, exportSchema = false)
abstract class UsersDataBase: RoomDatabase() {
    abstract fun usersDAO(): UsersDAO

    companion object {
        private var INSTANCE: UsersDataBase? = null

        fun getInstance(context: Context): UsersDAO {
            if (INSTANCE == null) {
                synchronized(UsersDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UsersDataBase::class.java,
                        "Users.db")
                        .build()
                }
            }
            return INSTANCE!!.usersDAO()
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}