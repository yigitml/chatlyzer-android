package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.ChatDeleteRequest
import com.ch3x.chatlyzer.data.remote.dto.ChatDto
import com.ch3x.chatlyzer.data.remote.ChatPostRequest
import com.ch3x.chatlyzer.data.remote.ChatPutRequest
import com.ch3x.chatlyzer.data.remote.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ChatApi {
    
    @GET("chat")
    suspend fun getChats():
            ApiResponse<List<ChatDto>>
    
    @GET("chat")
    suspend fun getChatById(
        @Query("id") id: String
    ): ApiResponse<ChatDto>

    @POST("chat")
    suspend fun createChat(
        @Body body: ChatPostRequest
    ): ApiResponse<ChatDto>

    @PUT("chat")
    suspend fun updateChat(
        @Body body: ChatPutRequest
    ): ApiResponse<ChatDto>

    @DELETE("chat")
    suspend fun deleteChat(
        @Body body: ChatDeleteRequest
    ): ApiResponse<ChatDto>
} 