package com.ch3x.chatlyzer.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {
    suspend fun saveGoogleIdToken(token: String)
    fun getGoogleIdToken(): Flow<String>

    suspend fun saveJwtToken(token: String)
    fun getJwtToken(): Flow<String>

    suspend fun saveTokenExpiresAt(timestamp: Long)
    fun isTokenExpired(): Flow<Boolean>

    suspend fun saveDeviceId(deviceId: String)
    fun getDeviceId(): Flow<String>

    suspend fun saveFcmToken(token: String)

    suspend fun saveUserInfo(
        userId: String,
        email: String,
        name: String,
        avatar: String
    )

    fun getUserId(): Flow<String>
    fun getUserEmail(): Flow<String>
    fun getUserName(): Flow<String>
    fun getUserAvatar(): Flow<String>

    suspend fun updateLastSyncTime()
    fun getLastSyncTime(): Flow<Long>

    suspend fun incrementAppLaunchCount()
    fun getAppLaunchCount(): Flow<Int>

    suspend fun setOnboardingCompleted()
    fun isOnboardingCompleted(): Flow<Boolean>

    suspend fun updateLastAppOpenTime()

    suspend fun setLanguage(languageCode: String)
    fun getLanguage(): Flow<String>

    suspend fun setBiometricEnabled(enabled: Boolean)
    fun isBiometricEnabled(): Flow<Boolean>

    suspend fun updateCacheExpiry(timeMillis: Long)
    fun isCacheExpired(): Flow<Boolean>

    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun logout()
    suspend fun getAllStoredDataAsString(): String
}