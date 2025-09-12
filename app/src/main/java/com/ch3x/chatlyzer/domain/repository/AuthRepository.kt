package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.data.remote.AuthMobilePostRequest
import com.ch3x.chatlyzer.data.remote.dto.AuthDto
import com.ch3x.chatlyzer.data.remote.dto.LogoutDto
import com.ch3x.chatlyzer.data.remote.dto.TokenRefreshDto

interface AuthRepository {

    suspend fun getToken(
        authMobilePostRequest: AuthMobilePostRequest
    ): AuthDto

    suspend fun refreshToken(): TokenRefreshDto

    suspend fun logout(): LogoutDto
}