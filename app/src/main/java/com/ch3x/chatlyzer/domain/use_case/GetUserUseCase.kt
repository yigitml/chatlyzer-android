package com.ch3x.chatlyzer.domain.use_case

import com.ch3x.chatlyzer.data.remote.parseRetrofitError
import com.ch3x.chatlyzer.domain.model.User
import com.ch3x.chatlyzer.domain.repository.UserRepository
import com.ch3x.chatlyzer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): Flow<Resource<User>> = flow {
        try {
            emit(Resource.Loading())
            val result = userRepository.fetchUser()
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(parseRetrofitError(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } /*catch (e: SerializationException) {
            Log.e("API", "Kotlinx serialization hatası: ${e.message}")
        } catch (e: IOException) {
            Log.e("API", "Ağ (network) hatası: ${e.message}")
        }
        */
    }
}