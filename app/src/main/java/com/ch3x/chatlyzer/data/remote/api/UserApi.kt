package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.UserPutRequest
import com.ch3x.chatlyzer.data.remote.dto.UserDto
import com.ch3x.chatlyzer.data.remote.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserApi {
    
    @GET("user")
    suspend fun getUser(): ApiResponse<UserDto>

    @PUT("user")
    suspend fun updateUser(
        @Body userPutRequest: UserPutRequest
    ): ApiResponse<UserDto>

    @DELETE
    suspend fun deleteUser(): ApiResponse<UserDto>
}