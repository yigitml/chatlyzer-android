package com.ch3x.chatlyzer.domain.repository

import com.ch3x.chatlyzer.data.remote.AnalysisDeleteRequest
import com.ch3x.chatlyzer.data.remote.AnalysisPostRequest
import com.ch3x.chatlyzer.data.remote.AnalysisPutRequest
import com.ch3x.chatlyzer.domain.model.Analysis

interface AnalysisRepository {
    suspend fun cacheAnalysis(
        analysis: Analysis
    )

    suspend fun cacheAnalyzes(
        analyzes: List<Analysis>
    )

    suspend fun updateCachedAnalysis(
        analysis: Analysis
    )

    suspend fun deleteCachedAnalysis(
        analysis: Analysis
    )

    suspend fun getCachedAnalysisById(
        analysisId: String
    ): Analysis?

    suspend fun getCachedAnalyzes(): List<Analysis>

    suspend fun clearCachedAnalyzes()

    suspend fun fetchAnalyzes(): List<Analysis>

    suspend fun fetchAnalysisById(
        analysisId: String
    ): Analysis?

    suspend fun fetchAnalysisByChatId(
        chatId: String
    ): List<Analysis>

    suspend fun createAnalysis(
        analysisPostRequest: AnalysisPostRequest
    ): List<Analysis>

    suspend fun updateAnalysis(
        analysisPutRequest: AnalysisPutRequest
    ): Analysis

    suspend fun deleteAnalysis(
        analysisDeleteRequest: AnalysisDeleteRequest
    ): Analysis
}