package com.example.githubclient.data

import androidx.room.*
import com.example.githubclient.data.entities.RepositoriesEntity
import com.example.githubclient.data.entities.UserProfileEntity

@Dao
interface UsersDAO {
    @Query("SELECT * FROM UserEntity ORDER BY id")
    fun getAllUsers(): List<UserProfileEntity>

    @Query("SELECT * FROM RepositoriesEntity WHERE userId=:userId")
    fun getAllRepositories(userId: Int): List<RepositoriesEntity>

    @Query("SELECT * FROM UserEntity WHERE id=:userId")
    fun getUser(userId: Int): UserProfileEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(entity: UserProfileEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRepo(entity: RepositoriesEntity)

    @Query("DELETE FROM UserEntity WHERE id=:userId")
    fun delete(userId: Int)

    @Query("DELETE FROM UserEntity")
    fun deleteAll()
}