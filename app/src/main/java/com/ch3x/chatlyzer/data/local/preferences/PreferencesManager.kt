package com.ch3x.chatlyzer.data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.ch3x.chatlyzer.util.Constants
import com.ch3x.chatlyzer.util.PreferenceKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = Constants.PREFERENCES_NAME
)

interface PreferencesManager {
    suspend fun <T> savePreference(key: Preferences.Key<T>, value: T)
    fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T>
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun clearAuthData()
    suspend fun clearAllPreferences()
    suspend fun <T> updatePreference(key: Preferences.Key<T>, defaultValue: T, transform: (T) -> T)
}

class PreferencesManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferencesManager {

    override suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        context.dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    override suspend fun <T> updatePreference(key: Preferences.Key<T>, defaultValue: T, transform: (T) -> T) {
        context.dataStore.edit { preferences ->
            val current = preferences[key] ?: defaultValue
            preferences[key] = transform(current)
        }
    }

    override fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
    }

    override fun isUserLoggedIn(): Flow<Boolean> {
        return context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception

            }
            .map { preferences ->
                preferences[PreferenceKeys.JWT_TOKEN]?.isNotEmpty() == true
            }
    }

    override suspend fun clearAuthData() {
        context.dataStore.edit { preferences ->
            preferences.remove(PreferenceKeys.JWT_TOKEN)
            preferences.remove(PreferenceKeys.EXPIRES_AT)
            preferences.remove(PreferenceKeys.GOOGLE_ID_TOKEN)
            preferences.remove(PreferenceKeys.USER_ID)
            preferences.remove(PreferenceKeys.USER_EMAIL)
            preferences.remove(PreferenceKeys.USER_NAME)
            preferences.remove(PreferenceKeys.USER_AVATAR)
        }
    }

    override suspend fun clearAllPreferences() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}