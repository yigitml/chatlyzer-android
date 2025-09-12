package com.ch3x.chatlyzer.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val email: String,
    val createdAt: Date,
    val isOnboarded: Boolean = false,
    val image: String? = null,
    val googleId: String? = null,
    val tokenVersion: Int = 0,
    val lastLoginAt: Date? = null,
    val isActive: Boolean = true,
    val updatedAt: Date,
    val deletedAt: Date? = null
)