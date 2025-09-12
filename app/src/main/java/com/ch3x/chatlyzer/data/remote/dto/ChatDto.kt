package com.ch3x.chatlyzer.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class ChatDto(
    @SerializedName("id")         val id: String,
    @SerializedName("userId")    val userId: String,
    @SerializedName("title")      val title: String?,
    @SerializedName("participants") val participants: List<String>,
    @SerializedName("createdAt")  val createdAt: Date,
    @SerializedName("updatedAt")  val updatedAt: Date,
    @SerializedName("deletedAt")  val deletedAt: Date? = null,

    // TODO: Make server send messages, analyzes and files along with the chat data

    // include: { messages: true, analyzes: true, files: True }

    // @SerializedName("messages") val messages: List<MessageDto> = emptyList(),
    // @SerializedName("analyzes") val analyzes: List<AnalysisDto> = emptyList(),
    // @SerializedName("files") val files: List<FileDto> = emptyList()
)