package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.AuthMobilePostRequest
import com.ch3x.chatlyzer.data.remote.dto.AuthDto
import com.ch3x.chatlyzer.data.remote.dto.LogoutDto
import com.ch3x.chatlyzer.data.remote.dto.TokenRefreshDto
import com.ch3x.chatlyzer.data.remote.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/mobile")
    suspend fun getToken(
        @Body authMobilePostRequest: AuthMobilePostRequest
    ): ApiResponse<AuthDto>

    @POST("auth/mobile/refresh")
    suspend fun refreshToken(): ApiResponse<TokenRefreshDto>

    @POST("auth/mobile/logout")
    suspend fun logout(): ApiResponse<LogoutDto>
}

