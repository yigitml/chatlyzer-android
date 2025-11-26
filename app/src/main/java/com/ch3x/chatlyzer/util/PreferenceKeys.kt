package com.ch3x.chatlyzer.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val GOOGLE_ID_TOKEN = stringPreferencesKey("google_id_token")
    val JWT_TOKEN = stringPreferencesKey("jwt_token")
    val EXPIRES_AT = longPreferencesKey("expires_at")

    val DEVICE_ID = stringPreferencesKey("device_id")
    val DEVICE_NAME = stringPreferencesKey("device_name")
    val APP_VERSION = stringPreferencesKey("app_version")
    val FCM_TOKEN = stringPreferencesKey("fcm_token")

    val USER_ID = stringPreferencesKey("user_id")
    val USER_EMAIL = stringPreferencesKey("user_email")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_AVATAR = stringPreferencesKey("user_avatar")

    val LAST_SYNC_TIME = longPreferencesKey("last_sync_time")
    val LAST_APP_OPEN_TIME = longPreferencesKey("last_app_open_time")
    val APP_LAUNCH_COUNT = intPreferencesKey("app_launch_count")
    val IS_FIRST_LAUNCH = booleanPreferencesKey("is_first_launch")

    val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")

    val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    val LANGUAGE_CODE = stringPreferencesKey("language_code")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
    val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")

    val CACHE_EXPIRY = longPreferencesKey("cache_expiry")
    val LAST_CACHE_CLEAR = longPreferencesKey("last_cache_clear")

    val PENDING_SHARED_FILE_URI = stringPreferencesKey("pending_shared_file_uri")
    val PENDING_SHARED_FILE_TYPE = stringPreferencesKey("pending_shared_file_type")
}