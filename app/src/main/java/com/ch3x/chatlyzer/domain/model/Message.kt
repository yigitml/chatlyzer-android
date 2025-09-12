package com.ch3x.chatlyzer.domain.model

import java.util.Date

data class Message(
    val id: String,
    val chatId: String,
    val sender: String,
    val content: String,
    val timestamp: Date,
    val metadata: Map<String, Any>? = null,
    val createdAt: Date,
    val updatedAt: Date,
    val deletedAt: Date? = null
)