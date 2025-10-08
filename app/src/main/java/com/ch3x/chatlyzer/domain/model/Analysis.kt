package com.ch3x.chatlyzer.domain.model

import java.util.Date

data class Analysis(
    val id: String,
    val chatId: String,
    val result: Map<String, Any>? = null,
    val createdAt: Date,
    val updatedAt: Date,
    val userId: String?,
    val deletedAt: Date? = null,
    val status: String
) 