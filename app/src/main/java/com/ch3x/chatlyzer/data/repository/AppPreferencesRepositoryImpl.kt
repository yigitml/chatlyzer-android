package com.ch3x.chatlyzer.data.repository

import android.hardware.usb.UsbDevice.getDeviceId
import com.ch3x.chatlyzer.data.local.preferences.PreferencesManager
import com.ch3x.chatlyzer.domain.repository.AppPreferencesRepository
import com.ch3x.chatlyzer.util.PreferenceKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppPreferencesRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
): AppPreferencesRepository {
    override suspend fun saveGoogleIdToken(token: String) =
        preferencesManager.savePreference(PreferenceKeys.GOOGLE_ID_TOKEN, token)

    override fun getGoogleIdToken(): Flow<String> =
        preferencesManager.getPreference(PreferenceKeys.GOOGLE_ID_TOKEN, "")

    override suspend fun saveJwtToken(token: String) =
        preferencesManager.savePreference(PreferenceKeys.JWT_TOKEN, token)

    override fun getJwtToken(): Flow<String> =
        preferencesManager.getPreference(PreferenceKeys.JWT_TOKEN, "")

    override suspend fun saveTokenExpiresAt(timestamp: Long) =
        preferencesManager.savePreference(PreferenceKeys.EXPIRES_AT, timestamp)

    override fun isTokenExpired(): Flow<Boolean> = getTokenExpiresAt()
        .map { expiry -> expiry < System.currentTimeMillis() }

    private fun getTokenExpiresAt(): Flow<Long> =
        preferencesManager.getPreference(PreferenceKeys.EXPIRES_AT, 0L)

    override suspend fun saveDeviceId(deviceId: String) =
        preferencesManager.savePreference(PreferenceKeys.DEVICE_ID, deviceId)

    override fun getDeviceId(): Flow<String> =
        preferencesManager.getPreference(PreferenceKeys.DEVICE_ID, "")

    override suspend fun saveFcmToken(token: String) =
        preferencesManager.savePreference(PreferenceKeys.FCM_TOKEN, token)

    override suspend fun saveUserInfo(
        userId: String,
        email: String,
        name: String,
        avatar: String
    ) {
        preferencesManager.savePreference(PreferenceKeys.USER_ID, userId)
        preferencesManager.savePreference(PreferenceKeys.USER_EMAIL, email)
        preferencesManager.savePreference(PreferenceKeys.USER_NAME, name)
        preferencesManager.savePreference(PreferenceKeys.USER_AVATAR, avatar)
    }

    override fun getUserId(): Flow<String> =
        preferencesManager.getPreference(PreferenceKeys.USER_ID, "")

    override fun getUserEmail(): Flow<String> =
        preferencesManager.getPreference(PreferenceKeys.USER_EMAIL, "")

    override fun getUserName(): Flow<String> =
        preferencesManager.getPreference(PreferenceKeys.USER_NAME, "")

    override fun getUserAvatar(): Flow<String> =
        preferencesManager.getPreference(PreferenceKeys.USER_AVATAR, "")

    override suspend fun updateLastSyncTime() =
        preferencesManager.savePreference(PreferenceKeys.LAST_SYNC_TIME, System.currentTimeMillis())

    override fun getLastSyncTime(): Flow<Long> =
        preferencesManager.getPreference(PreferenceKeys.LAST_SYNC_TIME, 0L)

    override suspend fun incrementAppLaunchCount() {
        getAppLaunchCount().first().let { count ->
            preferencesManager.savePreference(PreferenceKeys.APP_LAUNCH_COUNT, count + 1)
        }
    }

    override fun getAppLaunchCount(): Flow<Int> =
        preferencesManager.getPreference(PreferenceKeys.APP_LAUNCH_COUNT, 0)

    override  fun isOnboardingCompleted(): Flow<Boolean> =
        preferencesManager.getPreference(PreferenceKeys.ONBOARDING_COMPLETED, false)

    override suspend fun setOnboardingCompleted() {
        preferencesManager.savePreference(PreferenceKeys.ONBOARDING_COMPLETED, true)
    }

    override suspend fun updateLastAppOpenTime() =
        preferencesManager.savePreference(
            PreferenceKeys.LAST_APP_OPEN_TIME,
            System.currentTimeMillis()
        )

    override suspend fun setLanguage(languageCode: String) =
        preferencesManager.savePreference(PreferenceKeys.LANGUAGE_CODE, languageCode)

    override fun getLanguage(): Flow<String> =
        preferencesManager.getPreference(PreferenceKeys.LANGUAGE_CODE, "en")

    override suspend fun setBiometricEnabled(enabled: Boolean) =
        preferencesManager.savePreference(PreferenceKeys.BIOMETRIC_ENABLED, enabled)

    override fun isBiometricEnabled(): Flow<Boolean> =
        preferencesManager.getPreference(PreferenceKeys.BIOMETRIC_ENABLED, false)

    override suspend fun updateCacheExpiry(timeMillis: Long) =
        preferencesManager.savePreference(PreferenceKeys.CACHE_EXPIRY, timeMillis)

    override fun isCacheExpired(): Flow<Boolean> = getCacheExpiry()
        .map { expiry -> expiry < System.currentTimeMillis() }

    private fun getCacheExpiry(): Flow<Long> =
        preferencesManager.getPreference(PreferenceKeys.CACHE_EXPIRY, 0L)

    override fun isUserLoggedIn(): Flow<Boolean> = preferencesManager.isUserLoggedIn()
    override suspend fun logout() = preferencesManager.clearAuthData()

    override suspend fun getAllStoredDataAsString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("=== All Stored Preferences ===\n\n")
        
        // Auth Related
        stringBuilder.append("📱 AUTHENTICATION:\n")
        stringBuilder.append("Google ID Token: ${getGoogleIdToken().first()}\n")
        stringBuilder.append("JWT Token: ${getJwtToken().first()}\n")
        stringBuilder.append("Token Expires At: ${getTokenExpiresAt().first()}\n")
        stringBuilder.append("Is Token Expired: ${isTokenExpired().first()}\n\n")
        
        // Device Info
        stringBuilder.append("🔧 DEVICE INFO:\n")
        stringBuilder.append("Device ID: ${getDeviceId().first()}\n\n")
        
        // User Info
        stringBuilder.append("👤 USER INFO:\n")
        stringBuilder.append("User ID: ${getUserId().first()}\n")
        stringBuilder.append("Email: ${getUserEmail().first()}\n")
        stringBuilder.append("Name: ${getUserName().first()}\n")
        stringBuilder.append("Avatar: ${getUserAvatar().first()}\n\n")
        
        // App State
        stringBuilder.append("📊 APP STATE:\n")
        stringBuilder.append("Last Sync Time: ${getLastSyncTime().first()}\n")
        stringBuilder.append("App Launch Count: ${getAppLaunchCount().first()}\n\n")
        
        // Settings
        stringBuilder.append("⚙️ SETTINGS:\n")
        stringBuilder.append("Language: ${getLanguage().first()}\n")
        stringBuilder.append("Biometric Enabled: ${isBiometricEnabled().first()}\n\n")
        
        // Cache Control
        stringBuilder.append("💾 CACHE:\n")
        stringBuilder.append("Cache Expiry: ${getCacheExpiry().first()}\n")
        stringBuilder.append("Is Cache Expired: ${isCacheExpired().first()}\n\n")
        
        // Overall Status
        stringBuilder.append("🔐 STATUS:\n")
        stringBuilder.append("User Logged In: ${isUserLoggedIn().first()}\n")
        
        return stringBuilder.toString()
    }
}