package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.data.remote.PrivacyAnalysisPostRequest
import com.ch3x.chatlyzer.data.remote.dto.PrivacyAnalysisDto
import com.ch3x.chatlyzer.domain.model.Analysis

interface PrivacyAnalysisRepository {
  suspend fun createPrivacyAnalysis(
    privacyAnalysisPostRequest: PrivacyAnalysisPostRequest
  ): PrivacyAnalysisDto
}