package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.database.dao.AnalysisDao
import com.ch3x.chatlyzer.data.mapper.toDomain
import com.ch3x.chatlyzer.data.mapper.toEntity
import com.ch3x.chatlyzer.data.remote.AnalysisDeleteRequest
import com.ch3x.chatlyzer.data.remote.AnalysisPostRequest
import com.ch3x.chatlyzer.data.remote.AnalysisPutRequest
import com.ch3x.chatlyzer.data.remote.api.AnalysisApi
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.repository.AnalysisRepository
import com.ch3x.chatlyzer.data.remote.requireDataOrThrow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalysisRepositoryImpl @Inject constructor(
    private val analysisApi: AnalysisApi,
    private val analysisDao: AnalysisDao
): AnalysisRepository {
    override suspend fun cacheAnalysis(analysis: Analysis) =
        analysisDao.insertAnalysis(analysis.toEntity())

    override suspend fun cacheAnalyzes(analyzes: List<Analysis>) =
        analysisDao.insertAnalyzes(analyzes.map { it.toEntity() })

    override suspend fun updateCachedAnalysis(analysis: Analysis) =
        analysisDao.updateAnalysis(analysis.toEntity())

    override suspend fun deleteCachedAnalysis(analysis: Analysis) =
        analysisDao.deleteAnalysis(analysis.toEntity())

    override suspend fun getCachedAnalysisById(analysisId: String): Analysis? =
        analysisDao.getAnalysisById(analysisId)?.toDomain()

    override suspend fun getCachedAnalyzes(): List<Analysis> =
        analysisDao.getAllAnalyzes().map { it.toDomain() }

    override suspend fun clearCachedAnalyzes() =
        analysisDao.deleteAllAnalyzes()

    override suspend fun fetchAnalyzes(): List<Analysis> =
        analysisApi.getAnalyzes().requireDataOrThrow().map { it.toDomain() }

    override suspend fun fetchAnalysisById(analysisId: String): Analysis? =
        analysisApi.getAnalysisById(analysisId).requireDataOrThrow().toDomain()

    override suspend fun fetchAnalysisByChatId(chatId: String): List<Analysis> =
        analysisApi.getAnalysisByChatId(chatId).requireDataOrThrow().map { it.toDomain() }

    override suspend fun createAnalysis(analysisPostRequest: AnalysisPostRequest): List<Analysis> =
        analysisApi.createAnalysis(analysisPostRequest).requireDataOrThrow().map { it.toDomain() }

    override suspend fun updateAnalysis(analysisPutRequest: AnalysisPutRequest): Analysis =
        analysisApi.updateAnalysis(analysisPutRequest).requireDataOrThrow().toDomain()

    override suspend fun deleteAnalysis(analysisDeleteRequest: AnalysisDeleteRequest): Analysis =
        analysisApi.deleteAnalysis(analysisDeleteRequest).requireDataOrThrow()
            .toDomain()
}