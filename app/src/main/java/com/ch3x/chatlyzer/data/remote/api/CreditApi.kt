package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.dto.UserCreditDto
import com.ch3x.chatlyzer.data.remote.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface CreditApi {

    @GET("credit")
    suspend fun getCredits():
            ApiResponse<List<UserCreditDto>>
} 