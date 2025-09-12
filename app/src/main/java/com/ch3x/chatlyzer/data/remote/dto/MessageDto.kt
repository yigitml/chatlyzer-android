package com.ch3x.chatlyzer.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class MessageDto(
  @SerializedName("id")        val id: String,
  @SerializedName("chatId")   val chatId: String,
  @SerializedName("sender")    val sender: String,
  @SerializedName("content")   val content: String,
  @SerializedName("timestamp") val timestamp: Date,
  @SerializedName("metadata")  val metadata: String? = null,
  @SerializedName("createdAt") val createdAt: Date,
  @SerializedName("updatedAt") val updatedAt: Date,
  @SerializedName("deletedAt") val deletedAt: Date? = null
)
