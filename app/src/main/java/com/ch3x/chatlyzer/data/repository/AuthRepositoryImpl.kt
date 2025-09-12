package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.remote.AuthMobilePostRequest
import com.ch3x.chatlyzer.data.remote.api.AuthApi
import com.ch3x.chatlyzer.data.remote.dto.AuthDto
import com.ch3x.chatlyzer.data.remote.dto.LogoutDto
import com.ch3x.chatlyzer.data.remote.dto.TokenRefreshDto
import com.ch3x.chatlyzer.domain.repository.AuthRepository
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
): AuthRepository {
    override suspend fun getToken(authMobilePostRequest: AuthMobilePostRequest): AuthDto =
        authApi.getToken(authMobilePostRequest).requireDataOrThrow()

    override suspend fun refreshToken(): TokenRefreshDto =
        authApi.refreshToken().requireDataOrThrow()

    override suspend fun logout(): LogoutDto =
        authApi.logout().requireDataOrThrow()
}