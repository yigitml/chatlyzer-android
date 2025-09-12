package com.ch3x.chatlyzer.data.local.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
  tableName = "credits",
  foreignKeys = [
    ForeignKey(
      entity = UserEntity::class,
      parentColumns = ["id"],
      childColumns = ["userId"],
      onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
      entity = SubscriptionEntity::class,
      parentColumns = ["id"],
      childColumns = ["subscriptionId"],
      onDelete = ForeignKey.CASCADE
    )
  ],
  indices = [Index("userId"), Index("subscriptionId")]
)
data class CreditEntity(
  @PrimaryKey val id: String,
  val userId: String,
  val type: String,
  val subscriptionId: String,
  val totalAmount: Int = 0,
  val amount: Int = 0,
  val createdAt: Date,
  val updatedAt: Date,
  val minimumBalance: Int = 0,
  val deletedAt: Date? = null
) 