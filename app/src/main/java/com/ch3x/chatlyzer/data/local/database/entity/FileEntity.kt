package com.ch3x.chatlyzer.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
  tableName = "files",
  foreignKeys = [
    ForeignKey(
      entity = UserEntity::class,
      parentColumns = ["id"],
      childColumns = ["userId"],
      onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
      entity = ChatEntity::class,
      parentColumns = ["id"],
      childColumns = ["chatId"],
      onDelete = ForeignKey.CASCADE
    )
  ],
  indices = [Index("userId"), Index("chatId")]
)
data class FileEntity(
  @PrimaryKey val id: String,
  val url: String,
  val size: Int,
  val createdAt: Date,
  val userId: String,
  val chatId: String? = null,
  val deletedAt: Date? = null
) 