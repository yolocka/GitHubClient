package com.example.githubclient.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserEntity")
data class UserProfileEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userName: String,
    val userPhoto: String,
)