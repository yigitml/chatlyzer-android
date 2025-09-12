package com.ch3x.chatlyzer.data.remote.interceptor

import com.ch3x.chatlyzer.domain.repository.AppPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository
) {
    suspend fun getCurrentToken(): String? {
        val token = appPreferencesRepository.getJwtToken().first()
        return token.takeIf { it.isNotBlank() }
    }
}

class AuthTokenProvider @Inject constructor(
    private val tokenManager: TokenManager
) : TokenProvider {
    override suspend fun getToken(): String? {
        return tokenManager.getCurrentToken()
    }
}

interface TokenProvider {
    suspend fun getToken(): String?
}

@Module
@InstallIn(SingletonComponent::class)
object TokenModule {
    @Provides
    @Singleton
    fun provideTokenProvider(
        tokenManager: TokenManager
    ): TokenProvider = AuthTokenProvider(tokenManager)
}