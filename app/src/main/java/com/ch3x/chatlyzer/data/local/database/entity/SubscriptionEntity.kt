package com.ch3x.chatlyzer.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
  tableName = "subscriptions",
  foreignKeys = [
    ForeignKey(
      entity = UserEntity::class,
      parentColumns = ["id"],
      childColumns = ["userId"],
      onDelete = ForeignKey.CASCADE
    )
  ],
  indices = [Index("userId")]
)
data class SubscriptionEntity(
  @PrimaryKey val id: String,
  val name: String,
  val price: Double,
  val isActive: Boolean = false,
  val durationDays: Int,
  val createdAt: Date,
  val userId: String,
  val deletedAt: Date? = null
) 