package com.ch3x.chatlyzer.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class UserCreditDto(
  @SerializedName("id")               val id: String,
  @SerializedName("userId")          val userId: String,
  @SerializedName("type")             val type: String,
  @SerializedName("subscriptionId")  val subscriptionId: String,
  @SerializedName("totalAmount")     val totalAmount: Int = 0,
  @SerializedName("amount")           val amount: Int = 0,
  @SerializedName("createdAt")       val createdAt: Date,
  @SerializedName("updatedAt")       val updatedAt: Date,
  @SerializedName("minimumBalance")  val minimumBalance: Int = 0,
  @SerializedName("deletedAt")       val deletedAt: Date? = null
)
