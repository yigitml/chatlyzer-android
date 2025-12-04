package com.ch3x.chatlyzer.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ch3x.chatlyzer.data.local.database.converter.DateConverter
import java.util.Date

@Entity(
    tableName = "analyses",
    foreignKeys = [
        ForeignKey(
            entity = ChatEntity::class,
            parentColumns = ["id"],
            childColumns = ["chatId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [ Index("chatId"), Index("userId") ]
)
@TypeConverters(DateConverter::class)
data class AnalysisEntity(
    @PrimaryKey val id: String,
    val resultJson: String? = null,
    val createdAt: Date,
    val updatedAt: Date,
    val chatId: String?,
    val userId: String?,
    val deletedAt: Date? = null,
    val status: String
)