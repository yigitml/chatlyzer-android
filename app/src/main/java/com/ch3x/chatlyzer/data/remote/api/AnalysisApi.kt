package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.AnalysisDeleteRequest
import com.ch3x.chatlyzer.data.remote.AnalysisPostRequest
import com.ch3x.chatlyzer.data.remote.AnalysisPutRequest
import com.ch3x.chatlyzer.data.remote.dto.AnalysisDto
import com.ch3x.chatlyzer.data.remote.ApiResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AnalysisApi {

    @GET("analysis")
    suspend fun getAnalyzes():
            ApiResponse<List<AnalysisDto>>

    @GET("analysis")
    suspend fun getAnalysisById(
        @Query("id") analysisId: String
    ): ApiResponse<AnalysisDto>

    @GET("analysis")
    suspend fun getAnalysisByChatId(
        @Query("chatId") chatId: String
    ): ApiResponse<List<AnalysisDto>>

    @POST("analysis")
    suspend fun createAnalysis(
        @Body analysisPostRequest: AnalysisPostRequest
    ): ApiResponse<List<AnalysisDto>>

    @PUT("analysis")
    suspend fun updateAnalysis(
        @Body analysisPutRequest: AnalysisPutRequest
    ): ApiResponse<AnalysisDto>

    @DELETE("analysis")
    suspend fun deleteAnalysis(
        @Body analysisDeleteRequest: AnalysisDeleteRequest
    ): ApiResponse<AnalysisDto>
}