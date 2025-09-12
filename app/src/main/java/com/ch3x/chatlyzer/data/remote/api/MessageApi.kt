package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.dto.MessageDto
import com.ch3x.chatlyzer.data.remote.MessageDeleteRequest
import com.ch3x.chatlyzer.data.remote.MessagePostRequest
import com.ch3x.chatlyzer.data.remote.MessagePutRequest
import com.ch3x.chatlyzer.data.remote.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface MessageApi {

    @GET("message")
    suspend fun getMessageById(
        @Query("id") id: String,
    ): ApiResponse<MessageDto>

    @GET("message")
    suspend fun getMessagesByChatId(
        @Query("chatId") chatId: String,
    ): ApiResponse<List<MessageDto>>

    @POST("message")
    suspend fun createMessage(
        @Body messagePostRequest: MessagePostRequest
    ): ApiResponse<MessageDto>

    @PUT("message")
    suspend fun updateMessage(
        @Body messagePutRequest: MessagePutRequest
    ): ApiResponse<MessageDto>

    @DELETE("message")
    suspend fun deleteMessage(
        @Body messageDeleteRequest: MessageDeleteRequest
    ): ApiResponse<MessageDto>
}