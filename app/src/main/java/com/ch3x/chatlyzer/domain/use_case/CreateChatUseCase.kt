package com.ch3x.chatlyzer.domain.use_case

import com.ch3x.chatlyzer.data.remote.ChatPostRequest
import com.ch3x.chatlyzer.data.remote.parseRetrofitError
import com.ch3x.chatlyzer.domain.model.Chat
import com.ch3x.chatlyzer.domain.repository.ChatRepository
import com.ch3x.chatlyzer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class CreateChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    operator fun invoke(chatPostRequest: ChatPostRequest): Flow<Resource<Chat>> = flow {
        try {
            emit(Resource.Loading())
            val result = chatRepository.createChat(chatPostRequest)
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(parseRetrofitError(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }
} 