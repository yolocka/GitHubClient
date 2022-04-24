package com.example.githubclient.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "RepositoriesEntity")
data class RepositoriesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val userId: Int
    )