package com.ch3x.chatlyzer.domain.model

import java.util.Date

data class User(
    val id: String,
    val name: String,
    val email: String,
    val createdAt: Date,
    val isOnboarded: Boolean = false,
    val image: String? = null,
    val googleId: String? = null,
    val subscription: Subscription? = null,
    val tokenVersion: Int = 0,
    val lastLoginAt: Date? = null,
    val isActive: Boolean = true,
    val updatedAt: Date,
    val deletedAt: Date? = null
)