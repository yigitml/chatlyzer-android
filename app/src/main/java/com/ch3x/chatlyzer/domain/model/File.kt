package com.ch3x.chatlyzer.domain.model

import java.util.Date

data class File(
    val id: String,
    val url: String,
    val size: Int,
    val createdAt: Date,
    val userId: String,
    val chatId: String? = null,
    val deletedAt: Date? = null
)