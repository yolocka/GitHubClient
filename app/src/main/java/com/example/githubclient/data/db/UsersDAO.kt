package com.example.githubclient.data.db

import androidx.room.*
import com.example.githubclient.data.db.entities.RepositoriesEntity
import com.example.githubclient.data.db.entities.UserProfileEntity
import io.reactivex.rxjava3.core.Single

@Dao
interface UsersDAO {
    @Query("SELECT * FROM UserEntity ORDER BY userName")
    fun getAllUsers(): Single<List<UserProfileEntity>>

    @Query("SELECT * FROM RepositoriesEntity WHERE userId=:userId")
    fun getAllRepositories(userId: Long): Single<List<RepositoriesEntity>>

    @Query("SELECT * FROM UserEntity WHERE gitHubId=:userId")
    fun getUser(userId: Long): UserProfileEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(entity: UserProfileEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRepo(entity: RepositoriesEntity)

    @Query("DELETE FROM UserEntity WHERE id=:userId")
    fun delete(userId: Int)

    @Query("DELETE FROM UserEntity")
    fun deleteAll()
}