package com.ch3x.chatlyzer.data.repository

import com.ch3x.chatlyzer.data.local.preferences.PreferencesManager
import com.ch3x.chatlyzer.util.PreferenceKeys
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedDataRepository @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    suspend fun setSharedFile(fileUri: String, fileType: String) {
        preferencesManager.savePreference(PreferenceKeys.PENDING_SHARED_FILE_URI, fileUri)
        preferencesManager.savePreference(PreferenceKeys.PENDING_SHARED_FILE_TYPE, fileType)
    }

    suspend fun getPendingSharedFile(): Pair<String, String>? {
        val uri = preferencesManager.getPreference(PreferenceKeys.PENDING_SHARED_FILE_URI, "").firstOrNull()
        val type = preferencesManager.getPreference(PreferenceKeys.PENDING_SHARED_FILE_TYPE, "").firstOrNull()
        
        return if (!uri.isNullOrEmpty() && !type.isNullOrEmpty()) {
            Pair(uri, type)
        } else {
            null
        }
    }

    suspend fun clearPendingSharedFile() {
        preferencesManager.savePreference(PreferenceKeys.PENDING_SHARED_FILE_URI, "")
        preferencesManager.savePreference(PreferenceKeys.PENDING_SHARED_FILE_TYPE, "")
    }

    suspend fun hasPendingSharedFile(): Boolean {
        val uri = preferencesManager.getPreference(PreferenceKeys.PENDING_SHARED_FILE_URI, "").firstOrNull()
        return !uri.isNullOrEmpty()
    }
}