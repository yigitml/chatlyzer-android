package com.ch3x.chatlyzer.data.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedDataRepository @Inject constructor() {
    private var _pendingSharedFile: Pair<String, String>? = null

    fun setSharedFile(fileUri: String, fileType: String) {
        _pendingSharedFile = Pair(fileUri, fileType)
    }

    fun getPendingSharedFile(): Pair<String, String>? {
        return _pendingSharedFile
    }

    fun clearPendingSharedFile() {
        _pendingSharedFile = null
    }

    fun hasPendingSharedFile(): Boolean {
        return _pendingSharedFile != null
    }
}