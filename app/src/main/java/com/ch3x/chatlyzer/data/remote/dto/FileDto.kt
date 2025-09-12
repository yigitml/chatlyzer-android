package com.ch3x.chatlyzer.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class FileDto(
  @SerializedName("id")          val id: String,
  @SerializedName("url")         val url: String,
  @SerializedName("size")        val size: Int,
  @SerializedName("createdAt")  val createdAt: Date,
  @SerializedName("userId")     val userId: String,
  @SerializedName("chatId")     val chatId: String? = null,
  @SerializedName("deletedAt")  val deletedAt: Date? = null
)
