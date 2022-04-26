package com.example.githubclient.data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity", indices = [(Index(value = ["gitHubId"], unique = true))])
data class UserProfileEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val gitHubId: Long,
    val userName: String,
    val userPhoto: String,
)