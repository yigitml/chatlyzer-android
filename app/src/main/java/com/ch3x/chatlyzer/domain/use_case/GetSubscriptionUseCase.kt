package com.ch3x.chatlyzer.domain.use_case

import com.ch3x.chatlyzer.data.remote.parseRetrofitError
import com.ch3x.chatlyzer.domain.model.Subscription
import com.ch3x.chatlyzer.domain.repository.SubscriptionRepository
import com.ch3x.chatlyzer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetSubscriptionUseCase @Inject constructor(
    private val subscriptionRepository: SubscriptionRepository
) {
    operator fun invoke(): Flow<Resource<List<Subscription>>> = flow {
        try {
            emit(Resource.Loading())
            val result = subscriptionRepository.fetchSubscriptions()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(parseRetrofitError(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
}