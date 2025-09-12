package com.ch3x.chatlyzer.domain.model

import java.util.Date

data class Subscription(
    val id: String,
    val name: String,
    val price: Double,
    val credits: List<Credit> = emptyList(),
    val isActive: Boolean = false,
    val durationDays: Int,
    val createdAt: Date,
    val userId: String,
    val deletedAt: Date? = null
)