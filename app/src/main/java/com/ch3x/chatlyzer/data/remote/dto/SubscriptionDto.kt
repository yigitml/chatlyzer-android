package com.ch3x.chatlyzer.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class SubscriptionDto(
  @SerializedName("id")            val id: String,
  @SerializedName("name")          val name: String,
  @SerializedName("price")         val price: Double,
  @SerializedName("isActive")     val isActive: Boolean = false,
  @SerializedName("durationDays") val durationDays: Int,
  @SerializedName("createdAt")    val createdAt: Date,
  @SerializedName("userId")       val userId: String,
  @SerializedName("deletedAt")    val deletedAt: Date? = null
)
