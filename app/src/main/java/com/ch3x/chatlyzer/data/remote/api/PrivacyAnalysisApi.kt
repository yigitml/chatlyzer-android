package com.ch3x.chatlyzer.data.remote.api

import com.ch3x.chatlyzer.data.remote.ApiResponse
import com.ch3x.chatlyzer.data.remote.PrivacyAnalysisPostRequest
import com.ch3x.chatlyzer.data.remote.dto.AnalysisDto
import com.ch3x.chatlyzer.data.remote.dto.PrivacyAnalysisDto
import retrofit2.http.Body
import retrofit2.http.POST

interface PrivacyAnalysisApi {
  @POST("privacy-analysis")
  suspend fun createPrivacyAnalysis(
    @Body privacyAnalysisPostRequest: PrivacyAnalysisPostRequest
  ): ApiResponse<PrivacyAnalysisDto>
}