package com.ch3x.chatlyzer.data.remote.dto

data class AuthDto(
    val token: String,
    val expiresAt: Long,
    val user: AuthUser
) {
    data class AuthUser(
        val id: String,
        val email: String,
        val name: String,
        val avatarUrl: String
    )
}

data class TokenRefreshDto(
    val token: String,
    val expiresAt: Long
)

data class LogoutDto (
    val message: String
)
