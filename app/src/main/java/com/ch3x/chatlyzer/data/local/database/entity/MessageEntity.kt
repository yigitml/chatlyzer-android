package com.ch3x.chatlyzer.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
  tableName = "messages",
  foreignKeys = [
    ForeignKey(
      entity = ChatEntity::class,
      parentColumns = ["id"],
      childColumns = ["chatId"],
      onDelete = ForeignKey.CASCADE
    )
  ],
  indices = [Index("chatId")]
)
data class MessageEntity(
  @PrimaryKey val id: String,
  val chatId: String,
  val sender: String,
  val content: String,
  val timestamp: Date,
  val metadataJson: String? = null,
  val createdAt: Date,
  val updatedAt: Date,
  val deletedAt: Date? = null
) 