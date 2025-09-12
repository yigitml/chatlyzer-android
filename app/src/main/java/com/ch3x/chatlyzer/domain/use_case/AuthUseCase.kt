package com.ch3x.chatlyzer.domain.use_case

import com.ch3x.chatlyzer.data.remote.AuthMobilePostRequest
import com.ch3x.chatlyzer.data.remote.dto.AuthDto
import com.ch3x.chatlyzer.data.remote.parseRetrofitError
import com.ch3x.chatlyzer.domain.repository.AppPreferencesRepository
import com.ch3x.chatlyzer.domain.repository.AuthRepository
import com.ch3x.chatlyzer.domain.repository.UserRepository
import com.ch3x.chatlyzer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val preferencesRepository: AppPreferencesRepository,
    private val userRepository: UserRepository
) {
    operator fun invoke(authMobilePostRequest: AuthMobilePostRequest): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val jwt = preferencesRepository.getJwtToken().first()
            val isExpired = preferencesRepository.isTokenExpired().first()

            if (jwt.isNotBlank() && isExpired) {
                val refreshDto = authRepository.refreshToken()
                saveAuthData(
                    refreshDto.token,
                    refreshDto.expiresAt.toLong(),
                    authMobilePostRequest.accessToken
                )
                emit(Resource.Success(Unit))
                return@flow
            }

            val authDto = authRepository.getToken(authMobilePostRequest)
            saveFullAuthData(
                authDto,
                authMobilePostRequest.deviceId,
                authMobilePostRequest.accessToken
            )

            emit(Resource.Success(Unit))
        } catch (e: HttpException) {
            emit(Resource.Error(parseRetrofitError(e)))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        }
    }

    private suspend fun saveAuthData(token: String, expiresAt: Long, googleIdToken: String) {
        preferencesRepository.saveGoogleIdToken(googleIdToken)
        preferencesRepository.saveJwtToken(token)
        preferencesRepository.saveTokenExpiresAt(expiresAt)
    }

    private suspend fun saveFullAuthData(
        authDto: AuthDto,
        deviceId: String,
        googleIdToken: String
    ) {
        saveAuthData(authDto.token, authDto.expiresAt.toLong(), googleIdToken)
        preferencesRepository.saveUserInfo(
            authDto.user.id,
            authDto.user.email,
            authDto.user.name,
            authDto.user.avatarUrl
        )
        preferencesRepository.saveDeviceId(deviceId)
    }
}