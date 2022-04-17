package com.example.githubclient.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "RepositoriesEntity", foreignKeys = [
    ForeignKey(entity = UserProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = CASCADE)]
)
data class RepositoriesEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val userId: Int
    )