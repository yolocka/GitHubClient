package com.example.githubclient.data

import androidx.room.*
import com.example.githubclient.data.entities.UserProfileEntity

@Dao
interface UsersDAO {
    @Query("SELECT * FROM UserEntity ORDER BY userName")
    fun getAll(): List<UserProfileEntity>

    @Query("SELECT * FROM UserEntity WHERE id=:userId")
    fun getUser(userId: Int): UserProfileEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(entity: UserProfileEntity)

    @Query("DELETE FROM UserEntity WHERE id=:userId")
    fun delete(userId: Int)

    @Query("DELETE FROM UserEntity")
    fun deleteAll()
}