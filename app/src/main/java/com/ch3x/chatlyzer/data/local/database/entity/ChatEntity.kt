package com.ch3x.chatlyzer.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "chats",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class ChatEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String?,
    val participants: String,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date? = null,
    val isPrivacy: Boolean
)