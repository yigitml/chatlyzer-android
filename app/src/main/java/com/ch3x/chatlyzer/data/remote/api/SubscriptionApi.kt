package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.dto.SubscriptionDto
import com.ch3x.chatlyzer.data.remote.SubscriptionDeleteRequest
import com.ch3x.chatlyzer.data.remote.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface SubscriptionApi {

    @GET("subscription")
    suspend fun getSubscriptions():
            ApiResponse<List<SubscriptionDto>>

    @GET("subscription")
    suspend fun getSubscriptionById(
        @Query("id") id: String
    ): ApiResponse<SubscriptionDto>

    @DELETE("subscription")
    suspend fun deleteSubscription(
        @Body subscriptionDeleteRequest: SubscriptionDeleteRequest
    ): ApiResponse<SubscriptionDto>
}