package com.ch3x.chatlyzer.domain.model

import java.util.Date

data class Credit(
    val id: String,
    val userId: String,
    val type: String,
    val subscriptionId: String,
    val totalAmount: Int = 0,
    val amount: Int = 0,
    val createdAt: Date,
    val updatedAt: Date,
    val minimumBalance: Int = 0,
    val deletedAt: Date? = null
)