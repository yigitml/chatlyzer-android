package com.ch3x.chatlyzer.domain.use_case

import com.ch3x.chatlyzer.data.remote.parseRetrofitError
import com.ch3x.chatlyzer.domain.model.Analysis
import com.ch3x.chatlyzer.domain.repository.AnalysisRepository
import com.ch3x.chatlyzer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetAllAnalyzesUseCase @Inject constructor(
    private val analysisRepository: AnalysisRepository
) {
    operator fun invoke(): Flow<Resource<List<Analysis>>> = flow {
        try {
            emit(Resource.Loading())
            val result = analysisRepository.fetchAnalyzes()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(parseRetrofitError(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}