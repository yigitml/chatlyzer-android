package com.ch3x.chatlyzer.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AnalysisDto(
    @SerializedName("id")        val id: String,
    @SerializedName("result")    val result: Map<String, Any>? = null,
    @SerializedName("createdAt") val createdAt: Date,
    @SerializedName("updatedAt") val updatedAt: Date,
    @SerializedName("chatId")    val chatId: String?,
    @SerializedName("userId")    val userId: String?,
    @SerializedName("deletedAt") val deletedAt: Date? = null,
    @SerializedName("status")    val status: String
)