package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.database.dao.AnalysisDao
import com.ch3x.chatlyzer.data.mapper.toDomain
import com.ch3x.chatlyzer.data.remote.PrivacyAnalysisPostRequest
import com.ch3x.chatlyzer.data.remote.api.PrivacyAnalysisApi
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.repository.PrivacyAnalysisRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrivacyAnalysisRepositoryImpl @Inject constructor(
  private val privacyAnalysisApi: PrivacyAnalysisApi,
  private val analysisDao: AnalysisDao
) : PrivacyAnalysisRepository {
  override suspend fun createPrivacyAnalysis(
    privacyAnalysisPostRequest: PrivacyAnalysisPostRequest
  ): List<Analysis> =
    privacyAnalysisApi.createPrivacyAnalysis(privacyAnalysisPostRequest).requireDataOrThrow().map { it.toDomain() }
}