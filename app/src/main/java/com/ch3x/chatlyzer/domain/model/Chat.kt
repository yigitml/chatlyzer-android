package com.ch3x.chatlyzer.domain.model

import java.util.Date

data class Chat(
    val id: String,
    val title: String?,
    val participants: List<String>,
    val createdAt: Date,
    val updatedAt: Date,
    val userId: String,
    val messages: List<Message> = emptyList(),
    val analyzes: List<Analysis> = emptyList(),
    val files: List<File> = emptyList(),
    val deletedAt: Date? = null,
    val isPrivacy: Boolean
)