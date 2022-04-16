package com.example.githubclient.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubclient.data.entities.UserProfileEntity

@Database(entities = [UserProfileEntity::class], version = 1, exportSchema = false)
abstract class UsersDataBase: RoomDatabase() {
    abstract fun usersDAO(): UsersDAO
}